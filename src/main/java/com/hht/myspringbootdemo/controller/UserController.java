package com.hht.myspringbootdemo.controller;

import com.hht.myspringbootdemo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试用表(User)表控制层
 *
 * @author hanhaotian
 * @since 2019-11-08 14:50:59
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @RequestMapping("/async")
    public String async(){
        System.out.println("####IndexController####   1");
        userService.sendSms();
        System.out.println("####IndexController####   4");
        return "success";
    }

    @RequestMapping("/tran")
    public String transaction(){
        boolean b = userService.transactionTest();
        System.out.println(b);
        return "success";
    }

}