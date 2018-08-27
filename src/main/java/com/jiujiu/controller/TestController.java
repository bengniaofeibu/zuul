package com.jiujiu.controller;

import com.jiujiu.entity.UserInfo;
import com.jiujiu.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@Api("swaggerTest")
public class TestController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${pay.amount}")
    private String payAmount;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "这是一个Swagger测试",notes = "测试Swagger是否可行")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "测试id",paramType = "string",required = true,dataType ="Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "2001:因输入数据问题导致的报错"),
    })
    @PostMapping(value = "/test")
    public String test(@RequestBody @ApiParam(name = "用户对象",required = true) UserInfo userInfo){

        LOGGER.info("info 日志 {}",userInfo.getId());
        LOGGER.error("error 日志");

        userService.addUser(userInfo);
       return "hello world";
    }

    @GetMapping(value = "/get/payamout")
    public String  getConfig(){
         return  payAmount;
    }
}
