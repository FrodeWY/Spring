package com.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    final static String queueName = "spring-boot";
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setHost("localhost");
        factory.setPort(AMQP.PROTOCOL.PORT);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(new CachingConnectionFactory(connectionFactory));
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange directExchange() {
        DirectExchange direct_exchange = new DirectExchange("direct_exchange", true, false);
        return direct_exchange;
    }

    @Bean
    public Queue queue(){
        return new Queue("direct_queue",true,false,false);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange ){
        return BindingBuilder.bind(queue).to(exchange).with("direct_key");
    }


}
