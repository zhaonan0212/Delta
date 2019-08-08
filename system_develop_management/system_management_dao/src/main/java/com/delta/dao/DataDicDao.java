package com.delta.dao;


import com.delta.EchartResult;
import com.delta.domain.DataDictionary;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DataDicDao {

    @Select("select id,dname,short_name shortName,long_name longName,data_type dataType,pName from data_dictionary order by id asc")
    List<DataDictionary> findAll();

    @Select("select id, dname,short_name shortName,long_name longName,data_type dataType,pName from data_dictionary where id =#{id}")
    DataDictionary findById(Integer id);

    @SelectKey(statement = "select seq_data_dictionary.nextval from dual",keyProperty = "id",before = true,resultType = Integer.class)
    @Insert("insert into data_dictionary values (#{id},#{dname},#{shortName},#{longName},#{dataType},#{pName})")
    int add(DataDictionary dataDictionary);

    @Delete("delete from data_dictionary where id =#{id}")
    int delete(Integer id);


    @Update("update data_dictionary set dname = #{dname}, short_name = #{shortName},long_name = #{longName},data_type = #{dataType},pname = #{pName} where id = #{id} ")
    int update(DataDictionary dataDictionary);

    @Select("select id,dname,short_name shortName,long_name longName,data_type dataType,pName  from data_dictionary where dname like concat(concat('%',#{xname}),'%') or short_name like concat(concat('%',#{xname}),'%') or long_name like concat(concat('%',#{xname}),'%') or pname like concat(concat('%',#{xname}),'%') order by id asc")
    List<DataDictionary> searchByName(String xname);

    @Select("select data_type name,count(*) value from data_dictionary group by data_type")
    List<EchartResult> echart();

}

