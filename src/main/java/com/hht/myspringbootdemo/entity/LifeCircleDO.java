package com.hht.myspringbootdemo.entity;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;

/**
 * <br/>Description : bean的生命周期测试类.
 * 有三种方式可以设定Bean初始化后和销毁前执行的特定操作:
 * 1. 通过实现 InitializingBean/DisposableBean 接口来定制初始化之后/销毁之前的操作方法；
 * 2. 通过@bean注解的 init-method/destroy-method 属性指定初始化之后 /销毁之前调用的操作方法；
 * 3. @PostConstruct和@PreDestroy注解属于javaEE规范, 用来指定方法是在实例化之后还是销毁之前调用。
 *      如果生成对象时要完成依赖于依赖注入的初始化操作, 无法再构造函数中完成, 可以使用@PostConstruct完成.
 * <br/>CreateTime : 2021/10/19
 * @author hanhaotian
 */
@Data
@Component
public class LifeCircleDO implements InitializingBean, DisposableBean {
    private Long id;
    private String name;
    private String no;
    private LocalDateTime createTime;

    public LifeCircleDO() {
        System.out.println("LifeCircleDO-step 1. 执行构造方法");
    }

    public void setName() {

    }

    /**
     * 在对象执行构造函数创建完成, 属性赋值完成之后调用
     */
    @PostConstruct
    public void postConstruct() {
        this.name = "PostConstruct";
        System.out.println("LifeCircleDO-step 2. 执行被@PostConstruct注解的方法, 在实例化后 初始化前被调用...");
    }

    /**
     * 在容器销毁（移除）对象之前调用
     */
    @PreDestroy
    public void preDestory() {
        System.out.println("LifeCircleDO-shutdown-step 1. 执行被@PreDestroy注解的方法...");
    }

    /**
     * 在postConstruct之后被调用
     */
    @Override
    public void afterPropertiesSet(){
        System.out.println("LifeCircleDO-step 3. 执行实现InitializingBean接口的afterPropertiesSet()方法...");
    }

    @Override
    public void destroy(){
        System.out.println("LifeCircleDO-shutdown-step 2. 执行实现DisposableBean接口的destroy()方法...");
    }

    /**
     * 在afterPropertiesSet之后被调用
     */
    public void initMethod(){
        System.out.println("LifeCircleDO-step 4. 执行@bean中的initMethod...");
    }

    public void destroyMethod(){
        System.out.println("LifeCircleDO-shutdown-step 3. 执行@bean中的destroyMethod...");
    }

}
