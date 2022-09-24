package com.qihui.service;

import com.qihui.spring.Autowired;
import com.qihui.spring.Component;
import com.qihui.spring.Scope;

/**
 * @author chenqihui
 * @date 9/24/22
 */
@Scope()
@Component
public class UserService {

    @Autowired("orderService")
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }
}
