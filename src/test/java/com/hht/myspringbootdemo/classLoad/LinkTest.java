package com.hht.myspringbootdemo.classLoad;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/5/27
 */
public class LinkTest {

    private static LinkTest test3 = newInstance();
    private static LinkTest test1 = new LinkTest("test1");
    /**
     * 第一步:
     * 准备: 申请静态变量空间, 设置默认的初始值.
     * 此时a和b被设置为了默认的初始值 0
     */
    private static int a = 1;
    private static int b = 2;
    private static final int c = 3;
    //静态代码和静态代码块按声明的顺序先后加载
    private static LinkTest test2 = new LinkTest("test2");
    //private static LinkTest test4 = newInstance();

    /**
     * 第二步:
     * 初始化: 对静态变量进行初始化，执行静态块，创建类的实例
     * 此时静态变量已被初始化, a为2
     */
    static {
        System.out.println("【LinkTest类静态块】a=" + a);
    }

    /**
     * 使用阶段: 调用构造方法
     */
    public LinkTest(String desc) {
        System.out.println("【LinkTest类构造方法】desc=" + desc + ", a=" + a + ", b=" + b + ", c=" + c + "【LinkTest类实例】" + this.toString());
    }

    public static LinkTest newInstance() {
        System.out.println("【LinkTest类静态方法】start");
        LinkTest test1 = new LinkTest("newInstance");
        System.out.println("【LinkTest类静态方法】end");
        return test1;
    }
}
