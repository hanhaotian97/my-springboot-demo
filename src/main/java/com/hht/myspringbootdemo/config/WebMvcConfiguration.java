package com.hht.myspringbootdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * WebMvc配置器
 *
 * @author shenwei
 */
//@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 添加路径和资源的映射
     *
     * @param registry      资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("docs.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    /**
     * 拦截器配置
     *
     * @param registry      资源注册器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //对用户端所有请求拦截
        /*registry.addInterceptor()
                .addPathPatterns("/user/**");*/
    }

    /**
     * CORS配置
     *
     * @param registry  cors注册器
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");
    }
}

