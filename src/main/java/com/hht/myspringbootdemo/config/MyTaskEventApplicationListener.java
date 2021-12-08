package com.hht.myspringbootdemo.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 自定义spring监听器, 当监听到事件发布后, 自动执行onApplicationEvent(event)方法, event为我们自定义的事件对象
 * <br/>CreateTime 2021/6/21
 * @author hanhaotian
 */
@Component
public class MyTaskEventApplicationListener implements ApplicationListener<MyTaskEvent> {
    @Override
    public void onApplicationEvent(MyTaskEvent event) {
        JSONObject source = (JSONObject) event.getSource();
        String email = source.get("email").toString();
        System.out.println("MyTaskEventApplicationListener: 已监听到事件发布, Send Emial to " + email + " Task:" + event.getSource());
    }
}

