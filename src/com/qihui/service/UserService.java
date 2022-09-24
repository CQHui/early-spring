package com.qihui.service;

import com.qihui.spring.Autowired;
import com.qihui.spring.BeanNameAware;
import com.qihui.spring.Component;
import com.qihui.spring.Scope;

/**
 * @author chenqihui
 * @date 9/24/22
 */
@Scope()
@Component
public class UserService implements BeanNameAware {

    @Autowired("orderService")
    private OrderService orderService;

    private String beanName;

    public void test() {
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
