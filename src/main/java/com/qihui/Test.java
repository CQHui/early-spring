package com.qihui;

import com.qihui.mapper.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class Test {
    public static void main(String[] args) {
//        SpringApplication.run(Test.class, args);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        System.out.println(userMapper.getName());
    }
}
