package com.delta.domain;

import java.util.List;

public class SysDevelopManaUser {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer gender;
    private String phoneNum;
    private List<SysDevelopManaRole> roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<SysDevelopManaRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysDevelopManaRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysDevelopManaUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
