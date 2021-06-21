package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 观察者类
 * <br/>CreateTime 2021/6/18
 */
public class NumObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {    //有被观察者发生变化，自动调用对应观察者的update方法
        NumObservable myObservable = (NumObservable) o;     //获取被观察者对象
        System.out.println("Data has changed to " + myObservable.data);
    }
}
