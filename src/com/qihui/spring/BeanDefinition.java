package com.qihui.spring;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public class BeanDefinition {
    private Class type;
    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
