package com.hht.myspringbootdemo.spring;

import com.hht.myspringbootdemo.config.LifeCircleConfig;
import com.hht.myspringbootdemo.entity.LifeCircleDO;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    @Test
    public void testBeanLifeCircle01(){
        //创建IOC容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCircleConfig.class);
        System.out.println("LifeCircleTest: 容器创建完成...");

        //对于多实例Bean, Spring容器只有在使用这个Bean时才会进行实例化.
        LifeCircleDO lifeCircleDO = (LifeCircleDO) context.getBean("lifeCircleDO");
        System.out.println("LifeCircleTest: 获取的name值为 " + lifeCircleDO.getName());

        //对于单实例容器关闭时调用bean的销毁方法,.对于多实例bean容器不会自动调用这个bean的销毁方法, 需要开发者自行调用
        context.close();
        System.out.println("LifeCircleTest: 容器销毁完成...");
    }
}
