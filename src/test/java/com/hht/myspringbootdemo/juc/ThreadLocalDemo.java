package com.hht.myspringbootdemo.juc;

import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

/**
 * <br/>Author hanhaotian
 * <br/>Description : ThreadLocal测试
 * <br/>CreateTime 2020/7/11
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                local.set(finalI + "");
                System.out.println(Thread.currentThread().getName() + "\t 本地线程变量被设置为：" + finalI);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "\t 获取的本地线程变量为：" + local.get());
            }, String.valueOf(i)).start();
        }
    }
}
