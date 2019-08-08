package com.delta.service.impl;

import com.delta.dao.PermissionDao;

import com.delta.domain.SysDevelopManaPermission;
import com.delta.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<SysDevelopManaPermission> findAll() {
         return permissionDao.findAll();
    }

    @Override
    public PageInfo<SysDevelopManaPermission> pageHelperList(int page, int size) {

        PageHelper.startPage(page, size);
        List<SysDevelopManaPermission> permissionList = permissionDao.findAll();
        PageInfo<SysDevelopManaPermission> pageInfo = new PageInfo(permissionList);
        return pageInfo;
    }

    @Override
    public int add(SysDevelopManaPermission permission) {
        return permissionDao.add(permission);
    }

    @Override
    public int delete(Integer id) {
        return permissionDao.delete(id);
    }

    @Override
    public SysDevelopManaPermission findById(Integer id) {
        return permissionDao.findById(id);

    }

    @Override
    public int update(SysDevelopManaPermission permission) {
        return  permissionDao.update(permission);
    }
}
