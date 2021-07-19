package com.hht.myspringbootdemo.config;

import com.hht.myspringbootdemo.annotation.ConditionalOnProperty;
import com.hht.myspringbootdemo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2021/6/30
 */
@Component
public class MyConfig {
    @Bean
    @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "dev")
    public User createUserBeanOnDevProfiles() {
        System.out.println("创建Bean......");
        return new User();
    }
}
