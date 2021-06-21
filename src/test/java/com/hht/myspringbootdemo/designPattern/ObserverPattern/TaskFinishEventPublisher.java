package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 自定义的时间发布器
 * <br/>CreateTime 2021/6/18
 */
@Data
public class TaskFinishEventPublisher {

    private List<TaskFinishEventListener> listeners = new ArrayList<>();

    //注册监听器
    public synchronized void register(TaskFinishEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    //移除监听器
    public synchronized boolean remove(TaskFinishEventListener listener) {
        return listeners.remove(listener);
    }

    //发布任务结束事件
    public void publishEvent(TaskFinishEvent event) {
        for (TaskFinishEventListener listener : listeners) {
            listener.onTaskFinish(event);
        }
    }
}
