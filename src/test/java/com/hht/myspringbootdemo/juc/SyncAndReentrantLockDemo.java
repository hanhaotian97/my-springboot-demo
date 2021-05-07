package com.hht.myspringbootdemo.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br/>Author hanhaotian
 * <br/>Description : synchronize 和 ReentrantLock 的区别
 *
 * 相同点：都是可重入锁
 * 不同点：
 * 1. 原始构成
 * - synchronized 是关键字，JVM实现的，底层使用的是 monitor 对象来完成，它锁住的是类对象/对象实例。
 * - lock是具体类(juc.locks.lock)，JDK实现的，加/解锁调用API。
 * 1. 使用方式
 * - synchronized 执行完JVM会自动让线程释放锁。
 * - Lock 手动调用unlock()方法释放锁，如果没有释放可能会产生死锁现象。
 * 1. 等待是否可中断
 * - synchronized 不可中断，除非抛出异常或正常结束。
 * - ReentrantLock 可以中断，设置超时方法 tryLock(long timeout, TimeUnit unit)，或者调用 interrupt() 方法中断。
 * 1. 加锁是否公平：默认都是非公平锁，但是 ReentrantLock 可以设置是否公平。
 * 1. 选择性通知（锁可以绑定多个condition）：
 * - synchronize 要么随机唤醒要么唤醒全部线程。使用wait()和notify/notifyAll()方法。
 * - ReentrantLock 可以使用分组唤醒来实现精确唤醒。将线程对象注册到condition实例中，调用Condition.signalAll()方法只会唤醒注册其中的所有等待线程。
 * 1. 性能已不是选择标准，而是是否要使用ReenTrantLock的这些高级功能。
 *
 *
 * [A打印5次 --> B打印10次 --> C打印15次] ...重复10次
 *
 * <br/>CreateTime 2020/7/10
 */
public class SyncAndReentrantLockDemo extends SuperSyncAndReentrantLockDemo{
    public static void main(String[] args) {
        SyncAndReentrantLockDemo syncAndReentrantLockDemo = new SyncAndReentrantLockDemo();
        syncAndReentrantLockDemo.synchronizedMethod();
        syncAndReentrantLockDemo.doSomething();

        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        },"B").start();
    }

    /**
     * synchronized的可重入实验 1
     */
    private void synchronizedMethod(){
        final Object object = new Object();
        new Thread(()->{
            synchronized (object){
                System.out.println(Thread.currentThread().getName()+"\t"+"外层....");
                synchronized (object){
                    System.out.println(Thread.currentThread().getName()+"\t"+"中层....");
                    synchronized (object){
                        System.out.println(Thread.currentThread().getName()+"\t"+"内层....");
                    }
                }
            }
        },"A").start();
    }

    public synchronized void doSomething() {
        System.out.println("child.doSomething()" + Thread.currentThread().getName());
        doAnotherThing(); // 调用自己类中其他的synchronized方法
    }
    private synchronized void doAnotherThing() {
        super.doSomething(); // 调用父类的synchronized方法
        System.out.println("child.doAnotherThing()" + Thread.currentThread().getName());
    }
}

class SuperSyncAndReentrantLockDemo {
    public synchronized void doSomething() {
        System.out.println("father.doSomething()" + Thread.currentThread().getName());
    }
}


/**
 * ReentrantLock的分组唤醒实验, 使用condition可以定向控制唤醒部分线程线程
 * 1. 即使是多个线程抢占式执行, 最终也是c1->c2-c3顺序执行的.
 * 2. 外层方法获取锁后内存方法会自动获取锁, 所以c1方法执行中调用c2, 也能获取到锁对象.
 */
class ShareResource {
    private int n = 1;
    private Lock lock = new ReentrantLock();

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (n != 1) {
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + n);
            }

            //执行完成，进行通知
            n = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "\t" + "print5()方法执行完毕, 线程已解锁---------------");
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (n != 2) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + n);
            }

            //执行完成，进行通知
            n = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "\t" +"print10()方法执行完毕, 线程已解锁---------------");
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (n != 3) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + n);
            }

            //执行完成，进行通知
            n = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "\t" + "print15()方法执行完毕, 线程已解锁---------------");
        }
    }
}