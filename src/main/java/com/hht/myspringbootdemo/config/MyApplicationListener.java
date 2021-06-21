package com.hht.myspringbootdemo.config;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/6/21
 * @author hanhaotian
 */
@Component
public class MyApplicationListener implements ApplicationListener<TaskEvent> {
    @Override
    public void onApplicationEvent(TaskEvent event) {
        String email = "xxxx@gmail.com";
        System.out.println("Send Emial to " + email + " Task:" + event.getSource());
    }
}

