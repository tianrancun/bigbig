package com.bigbig.controller;

import com.bigbig.util.common.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "say")
@Slf4j
//@Api("swaggerDemoController相关的api")
public class HelloWorld {

    /**
     * http://localhost:8080/swagger-ui.html
     */
   // @ApiOperation(value = "swagger测试请求接口", notes = "测试say helloworld")
   // @ApiImplicitParam(name = "content", value = "输入的内容", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/t1/{content}", method = RequestMethod.GET)
    public ServiceResponse<Boolean> sayHelloWorld(@PathVariable String content){
        log.info("say {}",content);
        String sayContent = "hello world!";
        return new ServiceResponse<>(sayContent);
    }

    @RequestMapping(value = "/t2/{yumen}", method = RequestMethod.GET)
    public String sayHelloWorld2(@PathVariable String yumen){
        log.info("say 2{}",yumen);
        return "hello world!";
    }
}