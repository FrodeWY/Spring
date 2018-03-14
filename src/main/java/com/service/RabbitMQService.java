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

        byte[] bytes = message.getBytes();
        /**
         * confirm模式最大的好处在于他是异步的，一旦发布一条消息，生产者应用程序就可以在等信道返回确认的同时继续发送下一条消息，
         * 当消息最终得到确认之后，生产者应用便可以通过回调方法来处理该确认消息，如果RabbitMQ因为自身内部错误导致消息丢失，就会发送一条nack消息，
         * 生产者应用程序同样可以在回调方法中处理该nack消息；*/
        channel.confirmSelect();// 开启confirm模式

        /**basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体*/
        //发送消息到队列中
        channel.basicPublish("direct_exchange","direct_key",
                new AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .priority(2)//设置优先级

//                .deliveryMode(1)//是否持久化
                .expiration("600000")//设置过期时间ms
                .build()
                ,bytes);
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
        /*channel.close();
        connection.close();*/
    }
    public void close(){

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
                    channel.basicAck(delivery,false);
                }

            }
        };
        channel.basicConsume("direct_queue", false,"myConsumerTag",defaultConsumer);
//        defaultConsumer.

    }

}
