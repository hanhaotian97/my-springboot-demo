package com.hht.myspringbootdemo.juc.threadPool;

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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //runnableTest();
        callableTest();
    }

    private static void callableTest() throws InterruptedException, ExecutionException {
        CallableTask callableTask = new CallableTask();
        FutureTask<Integer> future = new FutureTask<>(callableTask);
        new Thread(future).start();

        Thread.sleep(1000);
        System.out.println("主线程计算开始");
        System.out.println(future.isDone());
        System.out.println("子任务执行的结果为: " + future.get());
        System.out.println(future.isDone());
        System.out.println("主线程计算结束");

        //使用线程池, 获取任务的返回值
        CallableTask callableTask2 = new CallableTask();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future2 = executor.submit(callableTask2);
        executor.shutdown();

        System.out.println(future2.get());
    }

    private static void runnableTest() {
        RunnableTask runnableTask = new RunnableTask();
        Thread thread = new Thread(runnableTask);
        thread.start();
    }

    static class RunnableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("I am a runnable task");
            //如果runnable中出现异常不处理 会导致线程直接死亡
            int i = 1 / 0;
            try {
                int i2 = 1 / 0;
            } catch (Exception e) {
                System.out.println("子任务中出现一个异常");
            }
        }
    }

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在计算中...");
            int i = 1 / 0;
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
