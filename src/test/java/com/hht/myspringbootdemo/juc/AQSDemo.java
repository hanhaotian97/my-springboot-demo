package com.hht.myspringbootdemo.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * <br/>Author hanhaotian
 * <br/>Description : AQS的demo
 * <br/>CreateTime 2021/3/23
 */
public class AQSDemo {
    public static void main(String[] args) throws Exception {
        countDownLatch();
        //cyclicBarrier();
        //semaphore();
        //lockSupport();
    }

    /**
     * lockSupport演示
     */
    private static void lockSupport() {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "coming....");
            //阻塞：permit默认是O，调用park()方法会阻塞当前线程，直到permit被设置为1时park方法被唤醒，然后会将permit再次设置为O false并返回
            LockSupport.park();
            //多次park()会导致程序处于一直等待的状态
            //LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t" + "被B唤醒了");
        }, "A");
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "唤醒A线程");
            //唤醒：调用unpark(thread)方法会将线程的许可permit设置成1 true，并自动唤醒线程
            LockSupport.unpark(t1);
            //因为permit值最多为1，多次调用unpark也只给park一个通行证
            //LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t" + "A线程已唤醒");
        }, "B");
        t2.start();
    }

    /**
     * semaphore演示
     */
    private static void semaphore() {
        //6个线程中最多并发数为3
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "\t抢占了车位");
                    // 获得一个许可，没有许可则一直阻塞
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放一个许可
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }

    /**
     * cyclicBarrier演示
     */
    private static void cyclicBarrier() {
        int threadNum = 5;
        // 每个线程都执行到等待点进行等待，直到所有线程都执行到等待点，才会继续往下执行。
        // public CyclicBarrier(int parties, Runnable barrierAction) {}
        // parties 是参与线程的个数, barrierAction 是所有线程到达栅栏的执行动作(由最后await的线程执行).
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t所有线程执行完毕, 我是最后到达到的");
        });

        System.out.println(Thread.currentThread().getName() + "\t准备开始跑步, 请运动员到场");
        for (int i = 1; i <= threadNum; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t运动员准备到场:" + temp);
                try {
                    //await,挂起当前线程
                    cyclicBarrier.await();
                    //cyclicBarrier.reset();
                } catch (InterruptedException | BrokenBarrierException e) {
                    //线程中断异常, 屏障破裂异常
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t运动员准备就绪:" + temp);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "\t运动员出发:" + temp);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t运动员到达终点:" + temp);
            }).start();
        }
    }

    /**
     * countDownLatch
     */
    private static void countDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(12);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t执行");
                countDownLatch.countDown();
            }, "线程A" + i).start();
        }
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t执行");
                countDownLatch.countDown();
            }, "线程B" + i).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t所有子线程执行完毕, 继续执行主业务");

        System.out.println("----------");
        Thread.sleep(1000);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t执行");
                countDownLatch.countDown();
            }, "线程A" + i).start();
        }
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t执行");
                countDownLatch.countDown();
            }, "线程B" + i).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t所有子线程执行完毕, 继续执行主业务");
    }

}
