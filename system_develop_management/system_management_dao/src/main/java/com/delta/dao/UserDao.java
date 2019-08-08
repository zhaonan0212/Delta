package com.delta.dao;

import com.delta.domain.SysDevelopManaUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Select("SELECT id,username,email,password,gender,phoneNum FROM sys_develop_manager_user order by id asc")
    List<SysDevelopManaUser> findAll();

    @SelectKey(statement = "select seq_sys_develop_id.nextval from dual", before = true, keyProperty = "id", resultType = Integer.class)
    @Insert("insert into sys_develop_manager_user (id,username,email,PASSWORD,gender,phoneNum) values (#{id},#{username}，#{email},#{password},#{gender},#{phoneNum})")
    int add(SysDevelopManaUser user);

    @Select("delete from sys_develop_manager_user where id = #{id}")
    void delete(Integer id);

    @Select("select * from sys_develop_manager_user where id =#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "com.delta.dao.RoleDao.findByUserId"))
    })
    SysDevelopManaUser findById(Integer id);

    @Select("select * from sys_develop_manager_user where username = #{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "com.delta.dao.RoleDao.findByUserId")
            )
    })
    SysDevelopManaUser findByUserName(String username);

    @Select("select * from sys_develop_manager_user where username like concat(concat('%',#{xname}),'%') order by id asc")
    List<SysDevelopManaUser> searchByName(String xname);

    @Delete("delete from sys_user_role where userId = #{id}")
    int deleteUserRole(Integer id);

    @Insert("insert into sys_user_role (userId,roleId) values (#{userId},#{roleId})")
    int addRole(@Param(value = "userId") Integer userId,@Param(value = "roleId") Integer roleId);
}
