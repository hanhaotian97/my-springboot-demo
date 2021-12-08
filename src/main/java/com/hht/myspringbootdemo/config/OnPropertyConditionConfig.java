package com.hht.myspringbootdemo.config;

import com.hht.myspringbootdemo.annotation.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 引入spring配置
 * <br/>CreateTime 2021/6/30
 */
@Component
public class OnPropertyConditionConfig {
    @Bean
    @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "dev")
    public void createUserBeanOnDevProfiles() {
        System.out.println("createUserBeanOnDevProfiles: 当spring.profiles.active=dev时, 我会打印这句话.");
    }
}
