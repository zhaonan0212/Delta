package com.delta.dao;

import org.apache.ibatis.annotations.Select;

public interface ExceptionNumberDao {

    @Select("select e_exception from exception_number where num = #{num}")
    String findByNum(String num);

}
