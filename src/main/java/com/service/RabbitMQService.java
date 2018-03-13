package com.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMQService {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void send() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("direct_exchange","direct",true,false,null);
        channel.queueDeclare("direct_queue",true,false,false,null);
        channel.queueBind("direct_exchange","direct_queue","direct_routingKey");

    }

    public void send_template(){
//        rabbitTemplate.setqu
        rabbitTemplate.convertAndSend("");
    }
}
