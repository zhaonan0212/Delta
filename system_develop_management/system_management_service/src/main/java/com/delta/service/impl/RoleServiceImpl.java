package com.delta.service.impl;

import com.delta.dao.RoleDao;
import com.delta.domain.SysDevelopManaRole;
import com.delta.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<SysDevelopManaRole> findAll() {
        return roleDao.findAll();
    }

    @Override
    public int add(SysDevelopManaRole role) {
        return roleDao.add(role);
    }

    @Override
    public int delete(Integer id) {
        return roleDao.delete(id);
    }

    @Override
    public int addPermissionToRole(Integer roleId, List<Integer> ids) {
        int i = roleDao.deleteById(roleId);
        for (Integer id : ids) {

            i += roleDao.update(roleId,id);
        }
        return i;
    }

    @Override
    public SysDevelopManaRole findByRoleId(Integer roleId) {
        return roleDao.findByRoleId(roleId);
    }
}
