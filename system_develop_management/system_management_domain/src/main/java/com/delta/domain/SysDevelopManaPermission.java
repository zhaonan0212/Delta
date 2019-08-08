package com.delta.domain;

import java.util.List;

public class SysDevelopManaPermission {

    private Integer Id;
    private String pName;
    private String url;
    private Integer pid;
    private List<SysDevelopManaRole> roles;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<SysDevelopManaRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysDevelopManaRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysDevelopManaPermission{" +
                "Id=" + Id +
                ", pName='" + pName + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                ", roles=" + roles +
                '}';
    }
}
