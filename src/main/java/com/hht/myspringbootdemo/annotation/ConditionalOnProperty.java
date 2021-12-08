package com.hht.myspringbootdemo.annotation;

import com.hht.myspringbootdemo.config.OnPropertyCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 *  自定义Property条件装配注解, 只有包含指定的配置段且属性值与value相等时才会执行被注解的方法.
 * <br/>CreateTime 2021/6/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnPropertyCondition.class)
public @interface ConditionalOnProperty {
    /**
     * 指定application配置文件的属性
     */
    String value();
    String prefix() default "";

    /**
     * 属性必须包含的值
     */
    String havingValue() default "";
    boolean matchIfMissing() default false;
    boolean relaxedNames() default true;
}