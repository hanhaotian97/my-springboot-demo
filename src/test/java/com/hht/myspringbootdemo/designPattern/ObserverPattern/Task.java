package com.hht.myspringbootdemo.designPattern.ObserverPattern;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <br/>Author hanhaotian
 * <br/>Description : 任务定义
 * <br/>CreateTime 2021/6/18
 */
@Data
@AllArgsConstructor
class Task {
    private String name;
    private TaskFinishStatus status;
}

/**
 * 任务状态
 */
enum TaskFinishStatus {
    SUCCESS,
    FAIL;
}