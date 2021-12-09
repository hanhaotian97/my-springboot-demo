package com.hht.myspringbootdemo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * <br/>Description :
 * <br/>CreateTime : 2021/12/8
 * @author hanhaotian
 */
//@Component
public class MyBeanFactoryPostProcessor02 implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor02: 已执行 ");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
