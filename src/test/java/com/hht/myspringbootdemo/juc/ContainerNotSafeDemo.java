package com.hht.myspringbootdemo.juc;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 线程不安全的集合类
 * <br/>CreateTime 2020/7/9
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        //listNotSafe();

        Set<String> list = new HashSet<>();  //出现并发修改异常,不同线程间的添加元素导致
        //Set<String> list = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    private static void listNotSafe() {
        List<String> list = new ArrayList<>();
        //List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
