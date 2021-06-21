package com.hht.myspringbootdemo.classLoad;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/5/27
 */
public class InitTest {
    /**
     * 类的加载过程:
     * 1. 加载: 加载二进制代码到JVM
     * 2. 链接:
     *      2.1. 校验: 校验class文件是否符合当前JVM规范
     *      2.2. 准备: 申请静态变量空间, 设置默认的初始值
     *      2.3. 解析: 方法区符号引用替换为指针引用
     * 3. 初始化: 对静态变量进行初始化，执行静态块，创建类的实例
     */
    public static void main(String[] args) {
        LinkTest test2 = new LinkTest();
        System.out.println("Test2实例化结束"+test2.toString());
    }
}
