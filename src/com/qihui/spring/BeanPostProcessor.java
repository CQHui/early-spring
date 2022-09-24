package com.qihui.spring;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public interface BeanPostProcessor {
    void postProcessBeforeInitialization(String beanName, Object bean);
    void postProcessAfterInitialization(String beanName, Object bean);
}
