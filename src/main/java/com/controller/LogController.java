package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("logs")
    public void logs(){

        logger.debug("logs");
        logger.warn("logs");
    }
}
