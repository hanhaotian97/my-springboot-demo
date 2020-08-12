package com.hht.myspringbootdemo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <br/>Author hanhaotian
 * <br/>Description : CAS操作的ABA问题
 * <br/>CreateTime 2020/7/6
 */
public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("=========ABA问题出现=========");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            //暂停1s，保证线程t1先执行一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2020) + "\t" + atomicReference.get());
        }, "t2").start();


        //暂停2s，上面执行完成
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=========以下是ABA问题的解决=========");
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 初始版本号：" + atomicStampedReference.getStamp());
            //暂停1s，保证线程t4也进入了执行
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第一次修改后版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第二次修改后版本号：" + atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 初始版本号：" + atomicStampedReference.getStamp());
            //暂停3s，保证线程t3先执行一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean result = atomicStampedReference.compareAndSet(101, 2020, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 修改是否成功：" + result + "\t 当前实际值" + atomicStampedReference.getReference() + "\t 当前实际版本号" + atomicStampedReference.getStamp());
        }, "t4").start();
    }
}
