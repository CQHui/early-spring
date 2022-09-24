package com.qihui.service;

import com.qihui.spring.BeanPostProcessor;
import com.qihui.spring.Component;

import java.lang.reflect.Proxy;

/**
 * @author chenqihui
 * @date 9/24/22
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")) {
            System.out.println("print userService");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")) {
            return Proxy.newProxyInstance(CustomBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
                System.out.println("proxy bean here");
                return method.invoke(bean, args);
            });
        }
        return bean;
    }
}
