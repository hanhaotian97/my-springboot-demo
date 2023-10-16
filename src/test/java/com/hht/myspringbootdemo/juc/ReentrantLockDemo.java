package com.hht.myspringbootdemo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 可重入锁demo，synchronized和ReentrantLock都是属于可重入锁
    两者都是同一个线程每进入一次，计数器加1，等到计数器变为0时才释放锁。
    都是在用户态把加锁问题解决，避免进入内核造成阻塞，在synchronized引入轻量锁，偏向锁之后，两者性能差不多，官方推荐使用synchronized。
 *
 * PS : 多线程的测试不能在Junit4中进行，因为Junit4没有等待子线程完成再关闭主线程。
 * <br/>CreateTime 2020/7/10
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        demo1();
    }

    /**
     * 可重入锁实验
     */
    private static void demo1() {
        phone phone = new phone();

        // synchronized的可重入实验
        new Thread(phone::sendSMS, "t1").start();
        new Thread(phone::sendSMS, "t2").start();

        //等待3s
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n\n");

        // ReentrantLock的可重入实验
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }
}

class phone implements Runnable {
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getId() + "\t" + Thread.currentThread().getName() + "\t SMS");
        sendEmail();
    }
    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getId() + "\t" + Thread.currentThread().getName() + "\t #######Email");
    }

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t getTokenAndKey");
            set();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t #####set");
        } finally {
            lock.unlock();
        }
    }
}