package com.hht.myspringbootdemo.spring;

/**
 * <br/>Description : 声明周期测试
 * 执行顺序:
 * 1. BeanFactoryPostProcessor, 对可以任意bean处理
 * 2. 普通Bean构造方法
 * 3. 设置依赖或属性
 * 4. BeanPostProcessor, 在类中使用@PostConstruct注解, 对指定bean处理
 * 5. InitializingBean
 * 6. initMethod 。
 * <br/>CreateTime : 2021/12/8
 * @author hanhaotian
 */
public class LifeCircleTest {
}
