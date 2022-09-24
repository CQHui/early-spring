package com.qihui.service;

import com.qihui.spring.*;

/**
 * @author chenqihui
 * @date 9/24/22
 */
@Scope()
@Component
public class UserService implements BeanNameAware, InitializingBean {

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

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }
}
