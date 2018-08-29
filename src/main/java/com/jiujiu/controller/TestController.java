package com.jiujiu.controller;

import com.jiujiu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${pay.amount}")
    private String payAmount;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/get/payamout")
    public String  getConfig(){
         return  payAmount;
    }
}
