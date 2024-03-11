package com.hht.myspringbootdemo.config;

import com.hht.myspringbootdemo.entity.User;
import com.hht.myspringbootdemo.service.UserService;
import com.hht.myspringbootdemo.util.ApplicationContextUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

@Configuration
public class MyFilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogCostFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LogCostFilter");
        registration.setOrder(1);
        return registration;
    }

}

class LogCostFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    //过滤器是容器级别, 基于servlet的实现, 初始化在spring之前不能直接使用bean
    //@Resource
    //private UserService userService;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        UserService userService = ApplicationContextUtil.resolve(UserService.class);
        User user = userService.queryById(1);
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Execute cost=" + (System.currentTimeMillis() - start));
    }

    @Override
    public void destroy() {

    }
}