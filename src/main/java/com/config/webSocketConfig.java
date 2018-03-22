package com.config;

import com.web_socket_handler.DemoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@EnableWebSocket
public class webSocketConfig implements WebSocketConfigurer{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(demoHandler(),"/webSocketTest");
    }

    @Bean
    public DemoHandler demoHandler(){
        return new DemoHandler();
    }
}
