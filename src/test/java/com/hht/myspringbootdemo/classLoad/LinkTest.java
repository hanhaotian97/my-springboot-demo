package com.hht.myspringbootdemo.classLoad;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/5/27
 */
public class LinkTest {
    /**
     * 第一步:
     * 准备: 申请静态变量空间, 设置默认的初始值.
     * 此时a和b被设置为了默认的初始值 0
     */
    private static LinkTest test1 = new LinkTest();
    private static int a = 2;
    private static int b = 2;

    /**
     * 第二步:
     * 初始化: 对静态变量进行初始化，执行静态块，创建类的实例
     * 此时静态变量已被初始化, a为2
     */
    static {
        System.out.println("【LinkTest类静态块】a=" + a);
    }

    public LinkTest(){
        System.out.println("【LinkTest类构造方法】a=" + a);
        System.out.println("【LinkTest类构造方法】b=" + b);
        System.out.println("【LinkTest类实例】" + this.toString());
    }

    public static LinkTest newInstance(){
        LinkTest test1 = new LinkTest();
        System.out.println("【LinkTest类静态方法】");
        return test1;
    }
}
