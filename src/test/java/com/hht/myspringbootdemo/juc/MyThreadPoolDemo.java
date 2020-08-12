package com.hht.myspringbootdemo.juc;

import java.util.concurrent.*;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 第四种获取多线程的方式：线程池，源代码都是使用的ThreadPoolExecutor。
 * 1：继承thread类
 * 2：实现runnable接口
 * 3：实现callable接口，带有返回值，可以抛出异常，落地方法是call
 * <br/>CreateTime 2020/7/10
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //threadPoolInit();

        //生产下不会使用Executors工具类生成的线程池，因为阻塞队列的最大值为INT_MAX，所以需要自定义线程池ThreadPoolExecutor
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());

        try {
            for (int i = 0; i < 19; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务" + finalI);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void threadPoolInit() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);  //一池5个处理线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();  //一池1个处理线程
        //ExecutorService threadPool =   Executors.newCachedThreadPool();  //一池N个处理线程
        try {
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务" + finalI);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
