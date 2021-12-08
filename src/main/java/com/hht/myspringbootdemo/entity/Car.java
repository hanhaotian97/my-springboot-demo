package com.hht.myspringbootdemo.entity;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <br/>Description : bean的生命周期测试类
 * 通过实现 InitializingBean/DisposableBean 接口来定制初始化之后/销毁之前的操作方法；
 *
 * <br/>CreateTime : 2021/10/19
 * @author hanhaotian
 */
@Data
@Component
public class Car implements InitializingBean, DisposableBean {

    public Car() {
        System.out.println("step 1. car constructor...");
    }

    // 在对象创建完成并且属性赋值完成之后调用
    @PostConstruct
    public void postConstruct() {
        System.out.println("car @PostConstruct...");
    }

    // 在容器销毁（移除）对象之前调用
    @PreDestroy
    public void preDestory() {
        System.out.println("car @PreDestroy...");
    }

    /**
     * 会在容器关闭的时候进行调用
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("car destroy ing...");
    }

    /**
     * 容器initMethod
     */
    public void init() throws Exception {
        System.out.println("car initMethod...");
    }

    /**
     * 会在bean创建完成，并且属性都赋好值以后进行调用
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("car afterPropertiesSet...");
    }

}
