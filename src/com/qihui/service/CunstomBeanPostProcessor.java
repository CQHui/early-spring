package com.qihui.service;

import com.qihui.spring.BeanPostProcessor;
import com.qihui.spring.Component;

/**
 * @author chenqihui
 * @date 9/24/22
 */
@Component
public class CunstomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public void postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")) {
            System.out.println("print userService");
        }
    }

    @Override
    public void postProcessAfterInitialization(String beanName, Object bean) {

    }
}
