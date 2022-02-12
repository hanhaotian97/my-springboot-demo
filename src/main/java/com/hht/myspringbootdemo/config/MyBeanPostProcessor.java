package com.hht.myspringbootdemo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * <br/>Description : 描述
 * 后置方法会在实例化和依赖注入之后, 初始化的前后 对每个Bean调用, 所以可以获取容器中的对象
 * <br/>CreateTime : 2021/12/9
 * @author hanhaotian
 */
@Configuration
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private Environment environment;

    /**
     * 初始化前执行的后置处理器
     * 实例化、依赖注入后, 初始化之前执行
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCircleDO".equals(beanName)) {
            String property = environment.getProperty("spring.profiles.active");
            System.out.println("MyBeanPostProcessor-postProcessBeforeInitialization: lifeCircleDO对象为 "+bean.toString()+", property: " + property);
        }
        return bean;
    }

    /**
     * 初始化后执行的后置处理器
     * 实例化、依赖注入、初始化之后执行
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCircleDO".equals(beanName)) {
            System.out.println("MyBeanPostProcessor-postProcessAfterInitialization: lifeCircleDO对象为 "+bean.toString());
        }
        return bean;
    }
}
