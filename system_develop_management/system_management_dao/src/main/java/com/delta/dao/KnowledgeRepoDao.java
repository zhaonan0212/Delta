package com.delta.dao;

import com.delta.EchartResult;
import com.delta.domain.KnowledgeRepository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface KnowledgeRepoDao {

    @Select("select ee.id, k_category knowledgeCat ，e_exception exceptionNum，description_con descriptionCon ，pname pName, pdate pDate from exception_number dd inner join \n" +
            "(select aa.id,knowledge_cat,exception_num,description_con,k_category ，pname，pdate from knowledge_repository  aa inner join knowledge_category bb \n" +
            "on aa. knowledge_cat=bb.kid)ee on dd.num = ee.exception_num order by ee.id asc")
    List<KnowledgeRepository> findAll();

    @SelectKey(statement = "select seq_knowledge_repo.nextval from dual",keyProperty = "id",
            before = true,resultType = Integer.class)
    @Insert("insert into knowledge_repository (id,knowledge_cat,exception_num,description_con,pname,pdate) values " +
            "(#{id},#{knowledgeCat},#{exceptionNum},#{descriptionCon},#{pName},#{pDate})")
    int add(KnowledgeRepository knowledgeRepository);

    @Select("select id,knowledge_cat knowledgeCat, exception_num exceptionNum, description_con descriptionCon,pname pName,pdate pDate from exception_number dd inner join \n" +
            "(select * from knowledge_repository  aa inner join knowledge_category bb \n" +
            " on aa. knowledge_cat=bb.kid)ee on dd.num = ee.exception_num  where id = #{id}")
    KnowledgeRepository findById(Integer id);

    @Update("update knowledge_repository set knowledge_cat =#{knowledgeCat},exception_num =#{exceptionNum},description_con = #{descriptionCon},pname =#{pName},pdate = #{pDate} where id = #{id}")
    int update(KnowledgeRepository knowledgeRepository);

    @Select("delete from knowledge_repository where id =#{id}")
    void delete(Integer id);

    @Select("select ee.id, k_category knowledgeCat ，e_exception exceptionNum，description_con descriptionCon ，pname pName, pdate pDate from exception_number dd inner join \n" +
            "(select aa.id,knowledge_cat,exception_num,description_con,k_category ，pname，pdate from knowledge_repository  aa inner join knowledge_category bb \n" +
            "on aa. knowledge_cat=bb.kid)ee on dd.num = ee.exception_num  where pname like concat(concat('%',#{xname}),'%') or  description_con like concat(concat('%',#{xname}),'%')  order by ee.id asc")
    List<KnowledgeRepository> searchByName(String xname);

    @Select("select c.e_exception name,count(*) value from KNOWLEDGE_REPOSITORY r inner join exception_number c on r.exception_num = c.num group by c.e_exception")
    List<EchartResult> echarts();

}
