package com.hht.myspringbootdemo.config;

import com.hht.myspringbootdemo.util.LogUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 全局异常处理
 *
 * @author LinkinStar
 */
@ControllerAdvice
public class GlobalExceptionResolver {

    /**
     * 全局Exception捕获
     *
     * @param e     exception异常
     * @return      返回响应
     */
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    HashMap serviceCommonExceptionHandler(Exception e) {
        LogUtil.printErrorLog(e);
        return new HashMap(){{
            put("code", "-1");
            put("message", e.getMessage());
            put("data", null);
        }};
    }

}
