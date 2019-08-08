package com.delta.dao;

import org.apache.ibatis.annotations.Select;

public interface SystemCateDao {

    @Select("select s_category from system_category where num = #{number}")
    String findByNumber(String number);

}
