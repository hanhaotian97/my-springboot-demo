package com.hht.myspringbootdemo.juc.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br/>Description :
 * 多个线程之间按照顺序调用，实现ABC三个线程启动，要求如下：
 * <p>
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着，AA打印5次，BB打印10次，CC打印15次
 * …来10轮
 * <br/>CreateTime : 2023/11/2
 * @author hanhaotian
 */
public class OrderPrintNum2 {
    //A 1  B 2  C 3
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    private static final ExecutorService POOL_SERVICE = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        OrderPrintNum2 shareResource = new OrderPrintNum2();
      //  new Thread(() -> shareResource.printN(5), "AA").start();
      //  new Thread(() -> shareResource.printN(10), "BB").start();
      //  new Thread(() -> shareResource.printN(15), "CC").start();

        //线程池方式
        POOL_SERVICE.execute(new Thread(() -> shareResource.printN(5), "AA"));
        POOL_SERVICE.execute(new Thread(() -> shareResource.printN(10), "BB"));
        POOL_SERVICE.execute(new Thread(() -> shareResource.printN(15), "CC"));
    }

    public void printN(int eachNum) {
        System.out.println(Thread.currentThread().getName() + "\t" + "start print");
        for (int j = 0; j < 10; j++) {
            System.out.println(Thread.currentThread().getName() + "\t" + "获取锁");
            lock.lock();
            try {
                //1.判断
                if (eachNum == 5) {
                    while (number != 1) {
                        System.out.println(Thread.currentThread().getName() + "\t" + "c1 await");
                        c1.await();
                    }
                }
                if (eachNum == 10) {
                    while (number != 2) {
                        System.out.println(Thread.currentThread().getName() + "\t" + "c2 await");
                        c2.await();
                    }
                }
                if (eachNum == 15) {
                    while (number != 3) {
                        System.out.println(Thread.currentThread().getName() + "\t" + "c3 await");
                        c3.await();
                    }
                }
                //2.干活
                System.out.println(Thread.currentThread().getName() + "\t -----打印次数:" + eachNum);
                /*for (int i = 1; i <= eachNum; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }*/
                //通知
                if (eachNum == 5) {
                    number = 2;
                    System.out.println(Thread.currentThread().getName() + "\t" + "c2 signal");
                    c2.signal();
                }
                if (eachNum == 10) {
                    number = 3;
                    System.out.println(Thread.currentThread().getName() + "\t" + "c3 signal");
                    c3.signal();
                }
                if (eachNum == 15) {
                    number = 1;
                    System.out.println(Thread.currentThread().getName() + "\t" + "c1 signal");
                    c1.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "\t" + "释放锁");
                lock.unlock();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "打印完毕-------");
    }
}
