package com.bigbig.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "say")
public class HelloWorld {

    @RequestMapping(value = "/hello")
    public String sayHelloWorld(){
        return "hello world";
    }
}