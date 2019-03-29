package com.bigbig.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "say")
@Slf4j
public class HelloWorld {

    @RequestMapping(value = "/hello")
    public String sayHelloWorld(){
        log.info("say hello world!!");
        return "hello world!";
    }
}