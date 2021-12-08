package com.hht.myspringbootdemo.config;

import com.hht.myspringbootdemo.annotation.ConditionalOnProperty;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 条件装配匹配器, 只有matches()返回true的条件, 才会执行之后的方法
 * <br/>CreateTime 2021/6/30
 */
public class OnPropertyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnProperty.class.getName());
        String propertyName = (String) annotationAttributes.get("value");
        String value = (String) annotationAttributes.get("havingValue");
        //获取配置文件中的属性 与 注解中的属性进行匹配, 全部匹配同则注册bean
        String propertyValue = context.getEnvironment().getProperty(propertyName);
        return propertyValue.equalsIgnoreCase(value);
    }
}
