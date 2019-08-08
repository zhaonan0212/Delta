package com.delta.dao;

import com.delta.domain.SysDevelopManaPermission;
import com.delta.domain.SysDevelopManaRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    @Select("select role_id id,role_name roleName, role_desc roleDesc from sys_develop_manager_role order by role_id asc")
    List<SysDevelopManaRole> findAll();

    @Select("select * from  sys_user_role sur,sys_develop_manager_role sr where sur.roleId =sr.role_id and sur.userId = #{userId}")
    @Results({
            @Result(id = true, property = "id", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleDesc", column = "role_desc"),
            @Result(property = "permissions", column = "roleId", javaType = List.class,
                    many = @Many(select = "com.delta.dao.PermissionDao.findPermissionByRoleId"))
    })
    List<SysDevelopManaRole> findByUserId(Integer userId);

    @SelectKey(statement = "select seq_sys_develop_role.nextval from dual", keyProperty = "id", before = true, resultType = Integer.class)
    @Insert("insert into sys_develop_manager_role values (#{id},#{roleName},#{roleDesc})")
    int add(SysDevelopManaRole role);

    @Select("delete from  sys_develop_manager_role where role_id = #{id}")
    int delete(Integer id);

    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    int deleteById(Integer roleId);

    @Insert("insert into sys_role_permission (roleId,permissionId) values (#{roleId},#{id})")
    int update(@Param("roleId") Integer roleId, @Param("id") Integer id);

    @Select("select * from sys_develop_manager_role where role_id = #{roleId}")
    @Results({
            @Result(id = true, property = "id", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleDesc", column = "role_desc"),
            @Result(property = "permissions", column = "role_id", javaType = List.class,
                    many = @Many(select = "com.delta.dao.PermissionDao.findPermissionByRoleId"))

    })
    SysDevelopManaRole findByRoleId(Integer roleId);
}
