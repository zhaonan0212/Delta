package com.delta.domain;


import java.util.List;

public class SysDevelopManaRole {

    private Integer id;
    private String roleName;
    private String roleDesc;
    private List<SysDevelopManaUser>  users;
    private List<SysDevelopManaPermission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<SysDevelopManaUser> getUsers() {
        return users;
    }

    public void setUsers(List<SysDevelopManaUser> users) {
        this.users = users;
    }


    public List<SysDevelopManaPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysDevelopManaPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "SysDevelopManaRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }
}
