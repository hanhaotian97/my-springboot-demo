package com.hht.myspringbootdemo.jdk8;

import java.lang.ref.*;

/**
 * <br/>Description : 引用测试
 * <br/>CreateTime : 2023/10/24
 * @author hanhaotian
 */
public class ReferenceTest {
    public static void main(String[] args) {
        //softReferTest();
        //weakReferTest();
        //phantomReferTest();
        phantomReferUser();

    }

    private static void phantomReferUser() {
        User obj = new User();
        ReferenceQueue<User> QUEUE = new ReferenceQueue<>();
        UserPhantomReference phantomReference = new UserPhantomReference(obj, QUEUE);
        obj = null;
        System.gc();
        // 被清除的队列中取出被回收的对象，一般新开一个线程来监控
        while (true) {
            Reference<? extends User> poll = QUEUE.poll();
            if (poll!=null) {
                UserPhantomReference userPhantomReference = (UserPhantomReference) poll;
                // 对象被回收，删除对应数据
                userPhantomReference.databaseClient.remove();
                System.out.println("--obj is recovery--");
            }
        }
    }

    /**
     * 用户类
     */
    static class User {
        public DatabaseClient databaseClient;
        public User() {
            // 初始化客户端
            databaseClient = new DatabaseClient();
            // 创建时数据库创建数据
            this.databaseClient.create();
        }
    }
    /**
     * 数据库客户端
     */
    static class DatabaseClient {
        /**
         * 创建用户数据
         */
        public void create() {
            System.out.println("--数据库创建用户数据--");
        }
        /**
         * 删除用户数据
         */
        public void remove() {
            System.out.println("--数据库删除用户数据--");
        }
    }
    static class UserPhantomReference extends PhantomReference<User> {
        // 保存user的databaseClient 因为取不到user对象
        public DatabaseClient databaseClient;

        public UserPhantomReference(User referent, ReferenceQueue<? super User> q) {
            super(referent, q);
            this.databaseClient = referent.databaseClient;
        }
    }

    private static void weakReferTest() {
        User u = new User();
        WeakReference<User> studentWeakRef = new WeakReference<>(u);
        u = null;
        System.out.println(studentWeakRef.get());
        System.gc();
        System.out.println("After GC:" + studentWeakRef.get());//gc之后一定会被回收
    }

    private static void softReferTest() {
        // 调整JVM内存 -Xms1m -Xmx1m
        // 新建一个强引用对象
        User user = new User();
        // 创建软引用
        SoftReference<User> softReferUser = new SoftReference<>(user);

        System.out.println(softReferUser.get());
        user = null;  //释放强引用
        System.out.println(softReferUser.get());

        System.gc();
        System.out.println("After GC:" + softReferUser.get());

        byte[] b = new byte[1024 * 400];//分配一个大对象
        System.gc();
        System.out.println("After a big object:" + softReferUser.get());//由于内存紧张，软引用会被清除
    }

    private static void phantomReferTest() {
        User obj = new User();
        // 存储被回收的对象
        ReferenceQueue<User> queue = new ReferenceQueue<>();
        // 使用虚引用指向这个内存空间
        PhantomReference<User> phantomReference = new PhantomReference<>(obj, queue);
        //System.out.println(phantomReference.get()); // 获取不到 打印为null

        obj = null;  //释放强引用，此时只剩虚引用指向它
        System.gc();
        //虚引用的作用就是在对象被GC回收时能得到通知
        while (true) {
            // 轮询队列,从队列中删除对象并返回该对象
            Reference<? extends User> poll = queue.poll();
            if (poll != null) {
                System.out.println("--obj is recovery--");
                break;
            }
        }
    }
}
