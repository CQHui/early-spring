package com.qihui;

import com.qihui.mapper.OrderMapper;
import com.qihui.mapper.UserMapper;
import com.qihui.springmybatis.MybatisFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);


        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(MybatisFactoryBean.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
        applicationContext.refresh();


        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);

        System.out.println(userMapper.getName());
        System.out.println(orderMapper.getOrder());
    }
}
