package com.hht.myspringbootdemo.juc;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 死锁现象模拟
 * 死锁是指 在多线程的执行过程中，线程互相抢夺资源而造成的互相等待现象。
 * <br/>CreateTime 2020/7/11
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "锁A";
        String lockB = "锁B";
        new Thread(new HoldLockThread(lockA, lockB)).start();
        new Thread(new HoldLockThread(lockB, lockA)).start();
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
