package com.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@WebListener
public class WebContextListener implements ServletContextListener{
    /*RabbitMQ建议客户端线程之间不要共用Channel，至少要保证共用Channel的线程发送消息必须是串行的，但是建议尽量共用Connection*/
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        ConnectionFactory connectionFactory = (ConnectionFactory)context.getBean("connectionFactory");
        Connection connection = null;
        Channel channel=null;
        boolean durable=true;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            /**type：有direct、fanout、topic三种
             durable：true、false true：服务器重启会保留下来Exchange。警告：仅设置此选项，不代表消息持久化。即不保证重启后消息还在。
             autoDelete:true、false.true:当已经没有消费者时，服务器是否可以删除该Exchange。*/
            channel.exchangeDeclare("direct_exchange","direct",durable,false,null);
            channel.exchangeDeclare("fanout_exchange","fanout",durable,false,null);
            /**queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
             * 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、第五个参数为队列的其他参数*/
            /*channel.queueDeclare("direct_queue",durable,false,false,null);
            channel.queueBind("direct_queue","direct_exchange","direct_key");*/

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {

            try {
                if (channel != null) {
                    channel.close();
                }
                if (connection != null) {
                    connection.close();
                }
                context.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
