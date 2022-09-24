package com.qihui.spring;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public class ApplicationContext {
    private Class configClass;

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;
    }

    public Object getBean(String beanName) {
        return null;
    }
}
