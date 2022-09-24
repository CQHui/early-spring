package com.qihui.service;

import com.qihui.spring.ApplicationContext;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);

        Object beanName = applicationContext.getBean("userService");
        System.out.println(beanName);
    }
}