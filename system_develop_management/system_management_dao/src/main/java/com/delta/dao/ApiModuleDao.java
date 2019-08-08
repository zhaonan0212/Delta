package com.delta.dao;

import com.delta.EchartResult;
import com.delta.domain.ApiModule;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ApiModuleDao {

    @Select("select id,s_category systemCat,function ,pname pName ,pdate dateTime from api_module aa inner join system_category bb on aa.system_cat=bb.num order by id asc")
    List<ApiModule> findAll();


    @SelectKey(statement = "select sequence_id.nextval from dual",keyProperty = "id",before = true,resultType = Integer.class)
    @Insert("insert into api_module (id,system_cat,function,pname,pdate) values (#{id},#{systemCat},#{function},#{pName},#{dateTime})")
    int add(ApiModule apiModule);

    @Select("select id,system_cat systemCat,function ,pname pName ,pdate dateTime from api_module aa inner join system_category bb on aa.system_cat=bb.num where id = #{id}")
    ApiModule findById(Integer id);

    @Update("update api_module set system_cat = #{systemCat},function = #{function},pname = #{pName},pdate = #{dateTime} where id = #{id}")
    int update(ApiModule apiModule);

    @Delete("delete from api_module  where id =#{id}")
    void delete(Integer id);

    @Select("select id,s_category systemCat,function ,pname pName ,pdate dateTime from api_module aa inner join system_category bb on aa.system_cat=bb.num  where pname like concat(concat('%',#{xname}),'%') or function like concat(concat('%',#{xname}),'%') order by id asc")
    List<ApiModule> searchByName(String xname);

    @Select("select c.s_category name,count(*) value from api_module m inner join system_category c on m.system_cat = c.num group by c.s_category")
    List<EchartResult> echart();

}
