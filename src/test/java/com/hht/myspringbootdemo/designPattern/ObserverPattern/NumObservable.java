package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import java.util.Observable;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 被观察者类, 对应主题, 向被观察者中添加观察者, 当发生setData改变时, 通知所有的观察者
 * <br/>CreateTime 2021/6/18
 */
public class NumObservable extends Observable {
    int data = 0;

    public void setData(int i) {
        data = i;
        setChanged();    //标记此 Observable对象为已改变的对象
        notifyObservers();    //通知所有观察者
    }
}
