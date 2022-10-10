package com.qihui.springmybatis;

import com.qihui.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MybatisFactoryBean implements FactoryBean {

    private SqlSession sqlSession;

    public MybatisFactoryBean(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
        this.sqlSession = sqlSessionFactory.openSession();
    }

    @Override
    public Object getObject() throws Exception {
        return sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public Class<?> getObjectType() {
        return UserMapper.class;
    }
}
