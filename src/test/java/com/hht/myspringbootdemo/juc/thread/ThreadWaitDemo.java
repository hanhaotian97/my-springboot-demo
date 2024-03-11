package com.hht.myspringbootdemo.juc.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * <br/>Description : 描述 各种线程休眠方法的区别
 * <br/>CreateTime : 2021/7/20
 * @author hanhaotian
 */
public class ThreadWaitDemo {
    public static void main(String[] args) {
        interruptSleep();

        LockSupport.park();
    }

    /**
     * 中断sleep()中的线程,
     */
    private static void interruptSleep() {
        final Thread sleepThread = new Thread(() -> {
            System.out.println("thread begin");

            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //输出thread over.true
            System.out.println("thread over." + Thread.currentThread().isInterrupted());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        sleepThread.start();

        //绑定钩子, java进程在收到关闭信号(kill -15 <pid>)后，会执行所有绑定了shutdownHook的线程，确保这些绑定的线程都执行完了才真正关闭
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleepThread.interrupt();
                    //主线程等待子线程的结束
                    sleepThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("shutdown success");
            }
        }));

        //输出线程状态
        //while (Thread.activeCount() > 1) {
        System.out.println(sleepThread.getState());
        //}
    }
}
