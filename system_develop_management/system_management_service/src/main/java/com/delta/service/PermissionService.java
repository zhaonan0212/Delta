package com.delta.service;

import com.delta.domain.SysDevelopManaPermission;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PermissionService {

    List<SysDevelopManaPermission> findAll();

    PageInfo<SysDevelopManaPermission> pageHelperList(int page, int size);

    int add(SysDevelopManaPermission permission);

    int delete(Integer id);

    SysDevelopManaPermission findById(Integer id);

    int update(SysDevelopManaPermission permission);
}
