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
 * 3. 在指定方法上加上@PostConstruct 或@PreDestroy注解来制定该方法是在初始化之后还是销毁之前调用。实例化之后初始化之前被调用
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
        System.out.println("LifeCircleDO-step 1. constructor...");
    }

    // 在对象创建完成并且属性赋值完成之后调用
    @PostConstruct
    public void postConstruct() {
        this.name = "PostConstruct";
        System.out.println("LifeCircleDO-step 2. 执行被@PostConstruct注解的方法, 我会在实例化后初始化前被调用...");
    }

    // 在容器销毁（移除）对象之前调用
    @PreDestroy
    public void preDestory() {
        System.out.println("LifeCircleDO-shutdown-step 1. 执行被@PreDestroy注解的方法...");
    }

    /**
     * 会在bean创建完成，并且属性都赋好值以后进行调用
     */
    @Override
    public void afterPropertiesSet(){
        System.out.println("LifeCircleDO-step 3. 执行afterPropertiesSet(), 需要实现InitializingBean接口...");
    }

    /**
     * 会在容器关闭的时候进行调用
     */
    @Override
    public void destroy(){
        System.out.println("LifeCircleDO-shutdown-step 2. 执行destroy(), 需要实现DisposableBean接口...");
    }

    /**
     * 容器initMethod
     */
    public void initMethod(){
        System.out.println("LifeCircleDO-step 4. @bean中的initMethod...");
    }

    /**
     * 容器 destroyMethod
     */
    public void destroyMethod(){
        System.out.println("LifeCircleDO-shutdown-step 3. @bean中的destroyMethod...");
    }

}
