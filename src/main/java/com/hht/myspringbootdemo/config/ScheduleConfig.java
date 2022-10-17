package com.hht.myspringbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * <br/>Description : 设置定时任务为多线程
 * <br/>CreateTime : 2022/2/14
 * @author hanhaotian
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("springScheduler-");
        return scheduler;
    }
}
