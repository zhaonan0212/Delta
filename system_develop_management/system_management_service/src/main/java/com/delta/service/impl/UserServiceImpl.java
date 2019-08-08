package com.delta.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.delta.dao.UserDao;
import com.delta.domain.SysDevelopManaUser;
import com.delta.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;



    @Override
    public List<SysDevelopManaUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public PageInfo<SysDevelopManaUser> pageHelperList(int page, int size) {
        PageHelper.startPage(page, size);
        List<SysDevelopManaUser> userList = userDao.findAll();
        PageInfo<SysDevelopManaUser> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public int add(SysDevelopManaUser user) {
        return userDao.add(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public SysDevelopManaUser findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public PageInfo<SysDevelopManaUser> searchByName(int page, int size, String xname) {

        PageHelper.startPage(page, size);
        List<SysDevelopManaUser> userList = userDao.searchByName(xname);
        PageInfo<SysDevelopManaUser> pageInfo = new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public int addRoleToUser(Integer userId, List<Integer> ids) {
        int i =userDao.deleteUserRole(userId);
        for (Integer roleId : ids) {
            i += userDao.addRole(userId,roleId);
        }
        return i;
    }


}
