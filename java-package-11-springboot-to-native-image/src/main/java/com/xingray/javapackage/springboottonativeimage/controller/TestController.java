package com.xingray.javapackage.springboottonativeimage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequestMapping("/test")
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public String hello() {
        String nowString = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("+08:00")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        logger.info("TestController#hello, now:" + nowString);
        return "hello SpringBoot, now:" + nowString;
    }
}
