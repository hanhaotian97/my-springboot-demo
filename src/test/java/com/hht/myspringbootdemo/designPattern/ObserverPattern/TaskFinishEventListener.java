package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import lombok.AllArgsConstructor;

import java.util.EventListener;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 任务结束事件监听器的抽象接口
 * 继承标记接口EventListner表示该接口的实现类是一个监听器,同时在内部定义了事件发生时的响应方法onTaskFinish(event),接收一个TaskFinishEvent作为参数。
 * <br/>CreateTime 2021/6/18
 */
public interface TaskFinishEventListener extends EventListener {
    void onTaskFinish(TaskFinishEvent event);
}

/**
 * <br/>Author hanhaotian
 * <br/>Description : 任务结束事件监听器的具体接口, 任务结束时发送邮件, 将任务的执行结果发送给用户
 * <br/>CreateTime 2021/6/18
 */
@AllArgsConstructor
class MailTaskFinishListener implements TaskFinishEventListener {
    private String email;

    @Override
    public void onTaskFinish(TaskFinishEvent event) {
        System.out.println("-------- Send Email to [" + email + "], Task : " + event.getSource());
    }
}

/**
 * 另外一个监听器实现, 用于发送短信
 */
@AllArgsConstructor
class SmsTaskFinishListener implements TaskFinishEventListener {
    private String address;

    @Override
    public void onTaskFinish(TaskFinishEvent event) {
        System.out.println("-------- Send Message to [" + address + "], Task : " + event.getSource());
    }
}