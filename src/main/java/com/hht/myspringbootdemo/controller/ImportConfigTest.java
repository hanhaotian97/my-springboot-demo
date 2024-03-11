package com.hht.myspringbootdemo.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2023/10/18
 * @author hanhaotian
 */
@Configuration
//@Import({User.class})  //注入到Spring容器中
@Import({MyImportSelector.class})  //根据selectImports( )返回的全限定类名数组批量注入到Spring容器中
public class ImportConfigTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportConfigTest.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        // 遍历Spring容器中的beanName
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
