package com.jiujiu.service.impl;

import com.jiujiu.Listener.UserListener;
import com.jiujiu.entity.UserInfo;
import com.jiujiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addUser(UserInfo userInfo) {
        System.out.println("添加用户成功");

        //注册监听事件
        applicationContext.publishEvent(new UserListener(this,userInfo));

    }
}
