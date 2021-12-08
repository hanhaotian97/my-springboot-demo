package com.hht.myspringbootdemo.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * @author hanhaotian
 * <br/>Description : 监听spring内置事件 ContextRefreshedEvent
 *  ApplicationContext 被初始化或刷新时，ContextRefreshedEvent事件被发布, 回调方法被调用。
 * <br/>CreateTime 2021/6/17
 */
@Component
public class ContextRefreshedApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("\nContextRefreshedApplicationListener : 我被调用了, 接下来我会获取所有的controller。");
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RequestMapping.class);
        map.forEach((key,value) ->{
            Class<?> beanClass = value.getClass();
            RequestMapping annotation = beanClass.getAnnotation(RequestMapping.class);
            if (annotation != null){
                System.out.println("自定义的controller:"+ beanClass.getName() +", 访问路径:"+ Arrays.toString(annotation.value()));
            }
        });
        System.out.println("ContextRefreshedApplicationListener : 调用结束。\n");
    }
}
