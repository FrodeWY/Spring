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
public class RabbitMQConfig2 {



    @Bean
    public ConnectionFactory connectionFactory() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPassword("123456");
        connectionFactory.setUsername("root");
        connectionFactory.setVirtualHost("/root");
        connectionFactory.setPort(AMQP.PROTOCOL.PORT);
        return connectionFactory;
    }
}
