package com.hht.myspringbootdemo.juc.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <br/>Description : 定时执行任务的线程池, 在ThreadPoolExecutor的基础上支持执行延迟任务或者周期性的任务。
 * <br/>CreateTime : 2022/2/14
 * @author hanhaotian
 */
@Slf4j
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        //对于线程密集型的任务, 线程数可以为cpu核心数的一半
        int coreCount = Runtime.getRuntime().availableProcessors();
        int workerCount = 1;
        while (workerCount < coreCount) {
            workerCount <<= 1;
        }
        coreCount = coreCount/2;

        //timerExcuter();
        //scheduleWithFixedDelay();
        scheduleAtFixedRate();
    }


    /**
     * ScheduledThreadPoolExecutor:
     * 1. 基于相对时间进行周期循环或延时操作
     * 2. 可以设置多线程
     * 3. 线程异常后会新增一个线程保持线程总数一定
     * 4. 可以使用scheduleWithFixedDelay, 任务执行完成后, 在延迟delay时长后才开启下一个任务. 所以实际间隔时间是 workTime+delay
     *  spring自带的@scheduler设置为多线程时，同一任务前一次执行完成后才会执行下一次任务
     */
    private static void scheduleWithFixedDelay() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(6);
        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("scheduleWithFixedDelay-start...");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("scheduleWithFixedDelay-end...");
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);
        scheduled.shutdown();
    }

    /**
     * 如果workTime小于period那么下一次任务将在延迟period - workTime之后执行。
     * 如果workTime大于period那么下一次任务将在上一次任务完成之后立刻执行。
     * 如果此任务的任何执行时间超过其周期，则后续执行可能开始较晚，但不会并发执行。
     */
    private static void scheduleAtFixedRate() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(6);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("scheduleAtFixedRate-start...");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //log.info("scheduleAtFixedRate-end...");
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);
        scheduled.shutdown();
    }

    /**
     * 1.5后使用java.util.concurrent.ScheduledThreadPoolExecutor替代
     * timer类:
     * 1. Timer单线程执行所有task的
     * 2. Timer基于绝对时间的延时执行或周期执行，当系统时间改变，则任务的执行会受到的影响
     * 3. Timer不会捕获TimerTask的异常，只是简单地停止
     */
    @Deprecated
    private static void timerExcuter() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("time:");
            }
        }, 2000, 40);//2000表示第一次执行任务延迟时间，40表示以后每隔多长时间执行一次run里面的任务
    }
}
