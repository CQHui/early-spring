package com.qihui.spring;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public @interface Scope {
    String value() default "singleton";
}
