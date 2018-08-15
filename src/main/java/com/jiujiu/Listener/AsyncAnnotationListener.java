package com.jiujiu.Listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncAnnotationListener{

  @Async
  @EventListener
  public void sendSMS(UserListener userListener){
      System.out.println("发送信息"+userListener.getT().getName());
  }
}


