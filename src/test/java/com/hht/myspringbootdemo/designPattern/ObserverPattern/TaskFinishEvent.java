package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import java.util.EventObject;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 任务结束事件  TaskFinishEvent
 * 自定义事件类型TaskFinishEvent继承自JDK中的EventObject,构造时会传入Task作为事件源。
 * <br/>CreateTime 2021/6/18
 */
public class TaskFinishEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public TaskFinishEvent(Object source) {
        super(source);
    }
}
