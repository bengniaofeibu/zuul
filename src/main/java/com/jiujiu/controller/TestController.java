package com.jiujiu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("swaggerTest")
public class TestController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @ApiOperation(value = "这是一个Swagger测试",notes = "测试Swgger是否可行")
    @ApiImplicitParam(name = "id",value = "测试id",paramType = "string",required = true,dataType ="Integer")
    @PostMapping(value = "/test")
    public String test(@RequestBody String id){

        LOGGER.info("info 日志 {}",id);
        LOGGER.error("error 日志");

       return "hello world";
    }
}
