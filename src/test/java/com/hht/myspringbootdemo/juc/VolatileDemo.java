package com.hht.myspringbootdemo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <br/>Author hanhaotian
 * <br/>Description : Volatile关键字的练习
 * 三大特点：保证可见性，不保证原子性，有序性。
 * <br/>CreateTime 2020/7/5
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                //volatile无法保证原子性，线程中的操作会被其他线程覆盖。
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        //最少两个线程： main线程 和 GC线程
        while (Thread.activeCount() > 2) {
            //yield() 礼让线程，作用是：暂停当前正在执行的线程对象，并执行其他线程。
            Thread.yield();
        }

        //实际值小于2w，无法保证原子性
        System.out.println(Thread.currentThread().getName() + "\t int finally number value:" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t atomicInteger finally number value:" + myData.atomicInteger);
    }

    /**
     * volatile保证可见性：某个线程修改后，会及时通知其他线程，主内存的变量已经被修改。
     */
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        //新建线程，此时变量拷贝到了 线程的工作空间，并进行了赋值操作。
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in, now value is : " + myData.number);

            //暂停线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update to 60, now value is : " + myData.number);
        }, "AAA").start();

        //没有Volatile关键字：一直等待，子线程修改值后，其他线程并不知道，对其他线程不可见。
        while (myData.number == 0) {
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, now value is : " + myData.number);
    }
}

class MyData {
    //int number = 0;
    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

    //synchronize可以保证原子性，但是太重量级了
    /*public synchronized void addPlusPlus() {
        number++;
    }*/

    public void addPlusPlus() {
        number++;
    }

    //使用原子类解决volatile的原子性
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic() {
        atomicInteger.getAndIncrement();
    }

}