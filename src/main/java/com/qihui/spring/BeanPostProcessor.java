package com.qihui.spring;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(String beanName, Object bean);
    Object postProcessAfterInitialization(String beanName, Object bean);
}
