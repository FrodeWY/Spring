package com.config;




/*import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;*/
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class RabbitMQConfig {
/*

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory=new CachingConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setHost("192.168.16.162");
        factory.setPort(5672);
        factory.setVirtualHost("/root");
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
*/

    /*@Bean
    public DirectExchange directExchange() {
        DirectExchange direct_exchange = new DirectExchange("direct_exchange2", true, false);
        return direct_exchange;
    }

    @Bean
    public Queue queue(){
        return new Queue("direct_queue2",true,false,false);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange ){
        return BindingBuilder.bind(queue).to(exchange).with("direct_key2");
    }*/



}
