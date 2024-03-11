package com.hht.myspringbootdemo.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 死锁现象模拟
 * 死锁是指 在多线程的执行过程中，线程互相抢夺资源而造成的互相等待现象。<p>
 * 死锁产生的四个必要条件: <p>
 * 1）互斥性：当资源被一个线程占用时，别的线程不能使用只能等待。
 * <p> 取消互斥性: 若资源不被一个进程独占使用，那么死锁是肯定不会发生的, 但是互斥条件一般是无法破坏的
 * 2）不可抢占：资源请求者不能强制从资源占有者手中抢夺资源，资源只能由占有者主动释放。
 * <p> 取消抢占: 占有资源后, 继续申请占有其他资源超时后, 占有原资源者自动释放
 * <p>
 * 3）请求和保持：当资源请求者在请求其他资源的同时保持对原有资源的占有
 * <p>每个进程提出新的资源申请前，必须先释放它先前所占有的资源。
 * <p>
 * 4）循环等待：多个线程存在环路的锁依赖关系而永远等待下去，每一个进程已获得的资源同时被下一个进程所请求。
 * <br/> 实现资源有序分配策略，将系统的所有资源统一编号，所有进程只能采用按序号递增的形式申请资源
 * <br/>CreateTime 2020/7/11
 */
public class DeadLockDemo {
    public static Object lockObject = new Object();

    public static void main(String[] args) {
        //getDeadLock();
        //resolveDeadLock1();
        resolveDeadLock2();

        /*for (int i = 0; i < 5; i++) {
            getDeadReentrantLock();
        }*/
        //cpuHoldTest();
    }

    /**
     * 死锁现象: 线程1获取A对象锁, 线程2获取B对象锁; 此时线程1又想获取B对象锁, 线程2又想获取A对象锁; 它们都等着对象释放锁, 此时就称为死锁
     */
    private static void getDeadLock() {
        String lockA = "锁A";
        String lockB = "锁B";
        new Thread(new HoldLockThread(lockA, lockB)).start();
        new Thread(new HoldLockThread(lockB, lockA)).start();
    }

    /**
     * 解决死锁方案一: 改变两个线程获取锁的顺序
     * 解决循环等待
     */
    private static void resolveDeadLock1() {
        String lockA = "锁A";
        String lockB = "锁B";
        new Thread(new HoldLockThread(lockA, lockB)).start();
        new Thread(new HoldLockThread(lockA, lockB)).start();
    }

    /**
     * 解决死锁方案二: 使用可重入锁的tryLock, 或者设置锁超时时间
     * 线程1占有锁A想要继续占有锁B时, 此时锁B被线程2占有, tryLock失败释放锁A.
     */
    private static void resolveDeadLock2() {
        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();
        new Thread(new HoldReentrantLockThread(lockA, lockB)).start();
        new Thread(new HoldReentrantLockThread(lockB, lockA)).start();
    }

    /**
     * 单个线程无限循环执行占用锁, 导致高CPU占用
     */
    private static void cpuHoldTest() {
        CPUHoldLockThread cpuHoldLockThread1 = new CPUHoldLockThread();
        cpuHoldLockThread1.run();
        CPUHoldLockThread cpuHoldLockThread2 = new CPUHoldLockThread();
        cpuHoldLockThread2.run();
    }

    static class CPUHoldLockThread implements Runnable {
        @Override
        public void run() {
            synchronized (lockObject) {
                int i = 0;
                while (true) {
                    i++;
                }
            }
        }
    }
}

class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己拥有：" + lockA + "，尝试拥有：" + lockB);
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己拥有：" + lockA + "\t" + lockB);
            }
        }
    }
}

class HoldReentrantLockThread implements Runnable {
    private ReentrantLock lockA;
    private ReentrantLock lockB;

    public HoldReentrantLockThread(ReentrantLock lockA, ReentrantLock lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        lockA.lock();  //使用lock会出现死锁, 如果锁空闲将获取到锁. 如果锁被其他线程持有, 将禁用当前线程 设置为休眠状态, 直到当前线程获取到锁
        //if (lockA.tryLock()) { //避免死锁使用tryLock, 如果锁可用则获取锁, 并立即返回 true,  如果锁不可用返回 false, 继续向下执行代码
            try {
                System.out.println(Thread.currentThread().getName() + "\t 占有锁：" + lockA + "，尝试拥有：" + lockB);
                try {
                    lockB.lock();
                    //拿不到锁时等待一段时间，如果还拿不到锁返回false。如果一开始拿到锁或者在等待期间内拿到了锁，则返回true。
                    // if (lockB.tryLock(100, TimeUnit.MILLISECONDS)) {
                    //if (lockB.tryLock()) {
                        System.out.println(Thread.currentThread().getName() + "\t 占有锁：" + lockA + "\t 和:" + lockB);
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lockB.isHeldByCurrentThread()) {
                        lockB.unlock();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 占有锁：" + lockB + "\t 释放:" + lockB);
                    //break;
                }
            } finally {
                lockA.unlock();
                System.out.println(Thread.currentThread().getName() + "\t 占有锁：" + lockA + "\t 释放:" + lockA);
            }
        //}
    }
}