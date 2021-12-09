package com.hht.myspringbootdemo.config;

import com.hht.myspringbootdemo.entity.LifeCircleDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <br/>Description : 测试生命周期配置类
 * <br/>CreateTime : 2021/12/8
 * @author hanhaotian
 */
@Configuration
public class LifeCircleConfig {
    /**
     * \@Bean 是一个方法级别上的注解，主要用在@Configuration注解的类里，也可以用在@Component注解的类里，方法名为注册后的BeanId。
     * \@Bean 注解会产生一个bean并交给spring容器管理.
     *
     * Q: 我已经在定义的类上加了@Configration/@Component等注册Bean的注解了，为啥还要用@Bean呢？
     * A: 如果我想将第三方的类变成组件纳入spring管理(比如各种mq的jar包), 但是无法修改源代码, 也就不能使用@Component等注解了, 这个时候用@Bean就比较合适了.
     */
    @Bean(initMethod="initMethod", destroyMethod="destroyMethod")
    //@Scope("prototype") //设置为多实例bean
    public LifeCircleDO lifeCircleDO() {
        return new LifeCircleDO();
    }
}
