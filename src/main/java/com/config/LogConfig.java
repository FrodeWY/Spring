package com.config;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class LogConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("logbackConfigLocation","classpath:logback.xml");
        servletContext.addListener(LogbackConfigListener.class);
    }
}
