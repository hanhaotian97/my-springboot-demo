package com.hht.myspringbootdemo.aspect;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 切面类
 * <br/>CreateTime 2021/2/25
 * aspectj1.9
 * javac无法识别aspect语法,需要将java编译器改成使用Ajc进行编译. ajc也可以编译java代码.
 * 注意 : 编译器为ajc是无法识别lombok插件的,导致无法在编译期自动生成对应方法
 */
public aspect TxAspect {
    //pointcut loadPointCut():call (* HelloWorld.sayHello());

    //环绕切点
    void around ():call (* HelloWorld.sayHello()){
        System.out.println("Trans a ction Beg in ");
        proceed();
        System.out.println("Transaction End ");
    }
}
