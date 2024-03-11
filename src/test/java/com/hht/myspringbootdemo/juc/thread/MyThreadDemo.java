package com.hht.myspringbootdemo.juc.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * <br/>Description : 获取线程的方式
 * 1：继承thread类
 * 2：实现runnable接口, 没有返回值, run()方法异常只能在内部消化，不能往上继续抛
 * 3：实现callable接口, 带有返回值，是个泛型，和Future、FutureTask配合可以用来获取异步执行的结果.
 * Callable接口支持返回执行结果，需要调用FutureTask.getTokenAndKey()得到，此方法会阻塞主进程的继续往下执行直到获得结果，如果不调用不会阻塞。
 * callable接口的call()方法允许抛出异常
 * <br/>CreateTime : 2022/2/14
 * @author hanhaotian
 */
public class MyThreadDemo {

    public static void main(String[] args) throws Exception {
        //threadTest();
        //runnableTest();
        callableTest();
    }

    /**
     * 带返回值的callable接口测试
     */
    private static void callableTest() throws Exception {
        CallableTask callableTask = new CallableTask();
        FutureTask<Integer> future = new FutureTask<>(callableTask);
        new Thread(future).start();
        Thread.sleep(1000);
        //future.cancel(true);//取消任务

        //isDone() 任务完成返回true,正常终止\出现异常\取消都会返回true
        System.out.println("主线程-计算开始: " + future.isDone());
        //get()方法会等待计算完成然后返回结果. 如果任务出现异常会抛出, 无论call()方法中出现什么异常都会抛出ExecutionException.
        //System.out.println("主线程-子任务执行的结果为: " + future.get());
        //设置任务的超时时间, 超时抛出TimeoutException
        System.out.println("主线程-子任务执行的结果为: " + future.get(1000, TimeUnit.MILLISECONDS));
        System.out.println("主线程-计算结束: " + future.isDone());

        //使用线程池, 获取任务的返回值
        System.out.println("-----使用线程池获取结果-----");
        CallableTask callableTask2 = new CallableTask();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> future2 = pool.submit(callableTask2);
        pool.shutdown();
        System.out.println("线程池-子任务执行的结果为:" + future2.get());
    }


    private static void threadTest() {
        MyThread myThread = new MyThread();
        myThread.start();
    }

    /**
     * 继承Thread类新建一个线程
     */
    public static class MyThread extends Thread {
        public void run() {
            System.out.println("MyThread.run()");
        }
    }

    private static void runnableTest() {
        RunnableTask runnableTask = new RunnableTask();
        Thread thread = new Thread(runnableTask);
        thread.start();
    }

    /**
     * 如果已经继承一个类，就无法再继承Thread，此时可以实现Runnable接口。
     */
    static class RunnableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("RunnableTask...");
            //如果runnable中出现异常i不处理 会导致线程直接死亡
            int i = 1 / 0;
            try {
                int i2 = 1 / 0;
            } catch (Exception e) {
                System.out.println("子任务中出现一个异常");
            }
        }
    }

    /**
     * 有返回值的任务实现Callable接口。
     * 假设执行3s后返回一个计算接管
     */
    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("CallableTask-子线程在计算中...");
            //int produceException = 1 / 0;
            Thread.sleep(3000);
            int i = new Random().nextInt();
            System.out.println("CallableTask-子线程计算结果为:" + i);
            return i;
        }
    }
}
