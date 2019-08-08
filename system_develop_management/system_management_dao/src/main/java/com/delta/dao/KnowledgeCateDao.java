package com.delta.dao;

import org.apache.ibatis.annotations.Select;

public interface KnowledgeCateDao {

    @Select("select k_category from knowledge_category where kid = #{kid}")
    String findByKid(String kid);
}
