package com.qihui.mapper;

import org.apache.ibatis.annotations.Select;

public interface OrderMapper {
    @Select("select 'order'")
    String getOrder();
}
