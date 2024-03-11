package com.hht.myspringbootdemo.juc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 线程不安全的集合类
 * <br/>CreateTime 2020/7/9
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        listNotSafe();
        //setNotSafe();
    }

    /**
     * 模拟场景:多个线程对集合内容进行修改
     */
    private static void listNotSafe() {
        //出现并发修改异常ConcurrentModificationException,不同线程间的添加元素导致
        //List<String> list = new ArrayList<>();
        //不会出现异常
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + " : " + list);
            }, String.valueOf("线程-" + i)).start();
        }
    }

    private static void setNotSafe() {
        Set<String> list = new HashSet<>();
        //Set<String> list = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
