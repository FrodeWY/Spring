package com.service;

import com.domain.Animal;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMQService {
    @Autowired
    private ConnectionFactory connectionFactory;

    public void send(String  message) throws IOException, TimeoutException {
        //创建一个新的连接
        Connection connection = connectionFactory.newConnection();
        //创建一个通道
        Channel channel=connection.createChannel();
        /*消息的持久化*/
        boolean durable=true;
        /*首先，我们要确保RabbitMQ永远不会丢失消息队列，那就需要声明它为持久化存储,虽然这里的操作是正确的，
        但在这里依然可能不会生效，如果命名“hello”的队列在之前已经被创建（非持久化），
        现在已经存在了。RabbitMQ不允许你重新定义一个已经存在的消息队列，如果你尝试着去修改它的某些属性的话，
        那么你的程序将会报错。所以，这里你需要更换一个消息队列名称：*/
        channel.queueDeclare("direct_queue", durable, false, false, null);
        channel.queueBind("direct_queue","direct_exchange","direct_key");
        channel.queueDeclare("fanout_queue", durable, false, false, null);
        channel.queueBind("fanout_queue","fanout_exchange","");
        byte[] bytes = message.getBytes();
        /**
         * confirm模式最大的好处在于他是异步的，一旦发布一条消息，生产者应用程序就可以在等信道返回确认的同时继续发送下一条消息，
         * 当消息最终得到确认之后，生产者应用便可以通过回调方法来处理该确认消息，如果RabbitMQ因为自身内部错误导致消息丢失，就会发送一条nack消息，
         * 生产者应用程序同样可以在回调方法中处理该nack消息；*/
        channel.confirmSelect();// 开启confirm模式
        /**使用basicQos放来来设置消费者最多会同时接收多少个消息。这里设置为1，表示RabbitMQ同一时间发给消费者的消息不超过一条。这样就能保证消费者在处理完某个任务，
        并发送确认信息后，RabbitMQ才会向它推送新的消息，在此之间若是有新的消息话，将会被推送到其它消费者，若所有的消费者都在处理任务，那么就会等待。*/
//        channel.basicQos(1);
        /**basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体*/
        //发送消息到队列中
        channel.basicPublish("direct_exchange","direct_key",
                new AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .priority(2)//设置优先级
                .deliveryMode(2)//是否持久化 1:不持久化 2：持久化
                .expiration("600000")//设置过期时间ms
                .build()
                ,bytes);
       /* channel.basicPublish("fanout_exchange","",null,bytes);*/
        //设置了监听器
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long delivery, boolean multiple) throws IOException {
                System.out.println("Ack: deliveryTag = "+delivery+" multiple: "+multiple);
            }

            @Override
            public void handleNack(long delivery, boolean multiple) throws IOException {
                System.out.println("nack: deliveryTag = "+delivery+" multiple: "+multiple);

            }

        });
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    public void receive() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String routingKey = envelope.getRoutingKey();
                String contentType = properties.getContentType();
                long delivery = envelope.getDeliveryTag();
                String message =new String(body,"UTF-8");
                System.out.println("message:"+message);
                if (message.equals("hello")) {
                    channel.basicAck(delivery, true);
                } else if(message.equals("hello2")) {
//                    channel.basicAck(delivery,false);
                    /*channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                    deliveryTag:该消息的index
                    multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                    requeue：被拒绝的是否重新入队列*/
                    channel.basicNack(delivery,true,false);
                }else {
                    /**deliveryTag:该消息的index
                     multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。*/
                    channel.basicAck(delivery,false);
                }

            }
        };
        /*autoAck：是否自动ack，如果不自动ack，需要使用channel.ack、channel.nack、channel.basicReject 进行消息应答*/
        channel.basicConsume("direct_queue", false,"myConsumerTag",defaultConsumer);

    }

}
