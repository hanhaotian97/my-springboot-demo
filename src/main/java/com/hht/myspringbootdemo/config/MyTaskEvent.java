package com.hht.myspringbootdemo.config;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件对象, ApplicationEvent是抽象类必须由一个实体类继承.
 * @author hanhaotian
 */
public class MyTaskEvent extends ApplicationEvent {
    public MyTaskEvent(Object source) {
        super(source);
    }
}
