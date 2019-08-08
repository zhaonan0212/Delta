package com.delta.dao;

import com.delta.domain.SysDevelopManaPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PermissionDao {

    @Select("select * from sys_develop_manager_permission order by id asc")
    List<SysDevelopManaPermission> findAll();

    @Insert("insert into sys_develop_manager_permission(id,pname,url) values (#{id},#{pName},#{url})")
    int add(SysDevelopManaPermission permission);

    @Delete("delete from sys_develop_manager_permission where id =#{id}")
    int delete(Integer id);

    @Select("select * from sys_develop_manager_permission where id = #{id}")
    SysDevelopManaPermission findById(Integer id);

    @Update("update sys_develop_manager_permission set pname =#{pName},url=#{url} where id = #{id}")
    int update(SysDevelopManaPermission permission);

    @Select("select * from  sys_role_permission srp ，sys_develop_manager_permission sp where srp.permissionId = sp.id and srp.roleId = #{roleId}")
    List<SysDevelopManaPermission> findPermissionByRoleId(Integer roleId);
}
