package com.hht.myspringbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String async(){
        System.out.println("testController");
        return "testController";
    }
}




