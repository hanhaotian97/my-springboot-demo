package com.hht.myspringbootdemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2022/2/14
 * @author hanhaotian
 */
@EnableScheduling
@Component
@Slf4j
public class TaskDemo {
    //@Scheduled(cron = "*/5 * * * * *")
    public void test0() throws InterruptedException {
        log.info("test0, 5秒执行一次，每次执行sleep 5s");
        Thread.sleep(5000L);
    }

    /**
     * 在单线程执行下, 同一任务为串行执行, 上一个任务执行未完成, 不会执行下一个任务
     * 多线程下, 不是串行执行, 但上一个任务执行未完成, 任不会执行下一个任务
     * [2022-02-14 17:33:15.005] [springScheduler-1] test1, 5秒执行一次，每次执行sleep 8s
     * [2022-02-14 17:33:25.003] [springScheduler-5] test1, 5秒执行一次，每次执行sleep 8s
     * [2022-02-14 17:33:35.003] [springScheduler-3] test1, 5秒执行一次，每次执行sleep 8s
     * [2022-02-14 17:33:45.001] [springScheduler-6] test1, 5秒执行一次，每次执行sleep 8s
     * @throws InterruptedException
     */
    //@Scheduled(cron = "*/5 * * * * *")
    public void test1() throws InterruptedException {
        log.info("test1, 5秒执行一次，每次执行sleep 8s");
        Thread.sleep(8000L);
    }
}
