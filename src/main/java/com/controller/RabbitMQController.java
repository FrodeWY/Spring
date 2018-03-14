package com.controller;


import com.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
public class RabbitMQController {
    @Autowired
    private RabbitMQService rabbitMQService;

    @PostMapping("message")
    public void sendMessage(String message) throws IOException, TimeoutException {
        rabbitMQService.send(message);
    }
    @GetMapping("message")
    public void getMessage() throws IOException, TimeoutException {
        rabbitMQService.receive();

    }

}
