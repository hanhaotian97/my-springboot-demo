package com.hht.myspringbootdemo.aspect;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/2/25
 */
public class HelloWorld {
    public void sayHello() {
        System.out.println("Hello AspectJ 1");
    }

    public static void main(String args[]) {
        HelloWorld h = new HelloWorld();
        h.sayHello();
    }
}