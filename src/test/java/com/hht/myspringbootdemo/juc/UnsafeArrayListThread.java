package com.hht.myspringbootdemo.juc;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 非线程安全的arrayList
 * <br/>CreateTime 2020/6/9
 */
class UnsafeArrayListThread extends Thread{
    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UnsafeArrayList.arrayList.add(Thread.currentThread().getName()+" "+System.currentTimeMillis());
    }
}

class UnsafeArrayList {
    public static List arrayList = new ArrayList();
    public static void main(String[] args) {
        Thread []threadArray = new Thread[1000];
        for(int i=0;i<threadArray.length;i++){
            threadArray[i] = new UnsafeArrayListThread();
            threadArray[i].start();

        }

        for(int i=0;i<threadArray.length;i++){
            try {
                threadArray[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<arrayList.size();i++){
            System.out.println(arrayList.get(i));
        }

    }
}