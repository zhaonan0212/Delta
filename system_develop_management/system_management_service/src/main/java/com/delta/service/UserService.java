package com.delta.service;

import com.delta.domain.SysDevelopManaUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService  {

    List<SysDevelopManaUser> findAll();

    PageInfo<SysDevelopManaUser> pageHelperList(int page, int size);

    int add(SysDevelopManaUser user);

    void delete(Integer id);

    SysDevelopManaUser findById(Integer id);

    PageInfo<SysDevelopManaUser> searchByName(int page, int size, String xname);

    int addRoleToUser(Integer userId, List<Integer> ids);
}
