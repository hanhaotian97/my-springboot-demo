package com.hht.myspringbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.hht.myspringbootdemo.dao")
public class MyspringbootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringbootdemoApplication.class, args);
	}

}
