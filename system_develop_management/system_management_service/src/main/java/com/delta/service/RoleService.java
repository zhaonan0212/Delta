package com.delta.service;

import com.delta.domain.SysDevelopManaRole;

import java.util.List;

public interface RoleService {

    List<SysDevelopManaRole> findAll();

    int add(SysDevelopManaRole role);

    int delete(Integer id);

    int addPermissionToRole(Integer roleId, List<Integer> ids);

    SysDevelopManaRole findByRoleId(Integer roleId);
}



