package com.hht.myspringbootdemo.classLoad;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/3/11
 */
public class TestClassLoadPath {
    public static void main(String[] args) {
        System.out.println("\n我的线程上下文类加载器是："+Thread.currentThread().getContextClassLoader());
        System.out.println("\n我的类加载器是："+TestClassLoadPath.class.getClassLoader());
        System.out.println("\n我的parent类加载器是："+TestClassLoadPath.class.getClassLoader().getParent());
        System.out.println("\n我的parent.parent类加载器是："+TestClassLoadPath.class.getClassLoader().getParent().getParent());

        System.out.println("\nBootstrap ClassLoader加载的文件 \n"+System.getProperty("sun.boot.class.path"));
        System.out.println("\nExtension ClassLoader加载的文件: \n"+System.getProperty("java.ext.dirs"));
        System.out.println("\nApp ClassLoader加载的文件 \n"+System.getProperty("jav.aclass.path"));
    }
}
