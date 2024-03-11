package com.hht.myspringbootdemo.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 读写锁分离测试
 * 多线程同时读，单线程写
 * 写操作必须是原子操作，线程独占操作
 * <br/>CreateTime 2020/7/10
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //private Lock lock = new ReentrantLock();

    public void put(String k, Object v) {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t put前, " + k);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(k, v);
            System.out.println(Thread.currentThread().getName() + "\t ###put后");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void get(String k) {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get前");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = map.get(k);
            System.out.println(Thread.currentThread().getName() + "\t ####get后, " + o);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}