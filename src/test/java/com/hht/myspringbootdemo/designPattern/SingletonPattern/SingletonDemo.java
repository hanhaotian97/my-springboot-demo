package com.hht.myspringbootdemo.designPattern.SingletonPattern;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 多线程下的单例模式demo
 * <br/>CreateTime 2020/7/6
 */
public class SingletonDemo {
    //volatile保证构造方法执行顺序不会被编译器指令重排
    private static volatile SingletonDemo instance;

    public SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 构造器方法");
    }

    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //System.out.println(getInstance() == getInstance());
        //System.out.println(getInstance() == getInstance());
        //System.out.println(getInstance() == getInstance());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
