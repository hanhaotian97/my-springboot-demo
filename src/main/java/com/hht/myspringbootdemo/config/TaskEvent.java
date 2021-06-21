package com.hht.myspringbootdemo.config;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 * @author hanhaotian
 */
public class TaskEvent extends ApplicationEvent {
    public TaskEvent(Object source) {
        super(source);
    }
}
