package com.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConfig {



    @Bean
    public ConnectionFactory connectionFactory() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPassword("123456");
        connectionFactory.setUsername("root");
        connectionFactory.setVirtualHost("/root");
        connectionFactory.setPort(AMQP.PROTOCOL.PORT);
        connectionFactory.setAutomaticRecoveryEnabled(true);//启用自动连接恢复
        connectionFactory.setNetworkRecoveryInterval(5000);//如果恢复因异常失败(如. RabbitMQ节点仍然不可达),它会在固定时间间隔后进行重试(默认是5秒).
        return connectionFactory;
    }
}
