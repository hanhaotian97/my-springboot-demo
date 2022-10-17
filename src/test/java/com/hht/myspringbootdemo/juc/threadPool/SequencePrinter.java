package com.hht.myspringbootdemo.juc.threadPool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br/>Description :
 * 请实现n个线程，使之交替打印  1 - 100 * n，如：n个线程分别为：Printer1 和 Printer2, Printer3 ... PrinterN，最后输出结果为：
 * Printer1 — 1
 * Printer2 — 2
 * Printer3 — 3
 * ...
 * PrinterN - n
 * Printer1 — n + 1
 * Printer2 - n + 2
 * Printer3 - n + 3
 * ...
 * PrinterN - 2n
 * <br/>CreateTime : 2022/3/1
 * @author hanhaotian
 */

public class SequencePrinter {

    private PrintThread[] multiThreads;
    private final int printTimePerThread;
    private final Object lock = new Object();
    private int num;
    /**
     * @param num                线程个数
     * @param printTimePerThread 每个线程打印次数
     */
    public SequencePrinter(int num, int printTimePerThread) {
        this.printTimePerThread = printTimePerThread;
        multiThreads = new PrintThread[num];
        this.num = num;
        for (int i = 0; i < num; i++) {
            multiThreads[i] = new PrintThread(i);
        }
    }

    public void startPrint() {
        for (PrintThread printThread : multiThreads) {
            printThread.start();
        }
    }


    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final Condition condition = reentrantLock.newCondition();
    private int count = 0;
    public class PrintThread extends Thread {
        private final int seq;

        public PrintThread(int seq) {
            this.seq = seq;
        }

        @Override
        public void run() {
            // 请补充
            String threadNo = Thread.currentThread().getName();
            // synchronized 实现
            while (true) {
                synchronized (lock) {
                    if (count >= printTimePerThread*num) {
                        break;
                    }
                    if (count % num == seq) {
                        System.out.println("Printer" + (seq + 1) + "-" + ++count);
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // reentrantLock 实现
            /*while (true) {
                try {
                    reentrantLock.lock();
                    if (count >= printTimePerThread*num) {
                        break;
                    }

                    int i2 = count % num;
                    if (i2 == seq) {
                        System.out.println("Printer" + (seq + 1) + "-" + ++count + "-"+threadNo);
                        condition.signalAll();
                    } else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    reentrantLock.unlock();
                }
            }*/
        }
    }

    public static void main(String[] args) {
        SequencePrinter printer = new SequencePrinter(3, 100);
        printer.startPrint();
    }
}
