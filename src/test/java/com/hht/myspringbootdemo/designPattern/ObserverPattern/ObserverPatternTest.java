package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import com.alibaba.fastjson.JSONObject;
import com.hht.myspringbootdemo.BaseTest;
import com.hht.myspringbootdemo.config.MyTaskEvent;
import com.hht.myspringbootdemo.config.MyTaskEventPublisher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 观察者模式
 * <br/>CreateTime 2021/6/18
 */
public class ObserverPatternTest extends BaseTest
{

    @Autowired
    private MyTaskEventPublisher myEventPublisher;

    /**
     * spring的自定义监听器
     */
    @Test
    public void testTaskEventSpringListener() {
        System.out.println("\ntestTaskEventSpringListener: 发送自定义邮件事件");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "xxxx@gmail.com");
        jsonObject.put("msg", "sign success!");
        myEventPublisher.publishEvent(new MyTaskEvent(jsonObject));
        System.out.println("testTaskEventSpringListener: 发送自定义邮件事件完成\n");
    }

    public static void main(String[] args) {
        observerTest();
        listenerTest();
    }

    /**
     * 事件监听模式 : 观察者模式的另一种形式.
     * 想象我们正在做一个关于Spark的任务调度系统,我们需要把任务提交到集群中并监控任务的执行状态,当任务执行完毕(成功或者失败),除了必须对数据库进行更新外,
     * 还可以执行一些额外的工作:比如将任务执行结果以邮件的形式发送给用户。这些额外的工作后期还有较大的变动可能:比如还需要以短信的形式通知用户,
     * 对于特定的失败任务需要通知相关运维人员进行排查等等,所以不宜直接写死在主流程代码中。
     * 最好的方式自然是以事件监听的方式动态的增删对于任务执行结果的处理逻辑。为此我们可以基于JDK提供的事件框架,打造一个能够对任务执行结果进行监听的弹性系统。
     */
    private static void listenerTest() {
        //事件源, 进行数据库更新, 此时成功了
        Task source = new Task("用户统计", TaskFinishStatus.SUCCESS);
        //任务结束事件
        TaskFinishEvent event = new TaskFinishEvent(source);

        //邮件服务监听器
        MailTaskFinishListener mailListener = new MailTaskFinishListener("hanhaotian@gmail.com");

        //事件发布器
        TaskFinishEventPublisher publisher = new TaskFinishEventPublisher();

        //注册邮件服务监听器
        publisher.register(mailListener);
        //注册短信服务监听器
        publisher.register(new SmsTaskFinishListener("1327671xxxxx"));

        //发布事件 : 其实就是遍历所有的监听器列表, 调用监听器接口自定义的通知方法
        publisher.publishEvent(event);
    }

    /**
     * 观察者模式:
     * numObservable添加了观察者对象, 当numObservable值发生改变时, 通知所有的观察者
     */
    private static void observerTest() {
        NumObservable number = new NumObservable();    //被观察者对象
        number.addObserver(new NumObserver());    //给number这个被观察者添加观察者(当然可以有多个观察者)
        number.setData(1);
        number.setData(2);
        number.setData(3);
    }
}
