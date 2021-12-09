package com.hht.myspringbootdemo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * <br/>Description :
 * BeanFactoryPostProcessor是针对BeanFactory的扩展,
 * 实现该接口在bean实例化之前被调用, 拿到beanFactory实例可以获取容器中所有的bean, 并修改Bean定义,
 * 但绝不能与bean实例交互, 这样做会导致bean过早实例化, 并导致意外的副作用. 如果需要 bean 实例交互，请考虑实现 BeanPostProcessor。
 * <br/>CreateTime : 2021/12/8
 * @author hanhaotian
 */
@Component
public class MyBeanFactoryPostProcessor01 implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //转换为子类，因为父类没有添加beanDefintion对象的api
        DefaultListableBeanFactory defaultbf = (DefaultListableBeanFactory) beanFactory;

        //可以通过beanFactory可以获取bean的定义信息，并可以修改bean的定义信息。
        BeanDefinition beanDefinition = defaultbf.getBeanDefinition("lifeCircleDO");
        System.out.println("MyBeanFactoryPostProcessor01: 已执行, 我会在bean实例化之前被调用");

        // 需要注意: 如果获取bean实例, 会导致BeanPostProcessor相关注解失效(@PostConstruct, @PreDestroy).
        //LifeCircleDO lifeCircleDO = (LifeCircleDO) beanFactory.getBean("lifeCircleDO");
        //System.out.println("MyBeanFactoryPostProcessor01: 取得的userName =  "+ lifeCircleDO.getName());
        //lifeCircleDO.setName("波尔舍");
        //System.out.println("MyBeanFactoryPostProcessor01: 将userName 设置成"+ lifeCircleDO.getName());
    }

    @Override
    public int getOrder() {
        //根据order属性自己定义执行顺序, 从小到大。
        return 1;
    }
}
