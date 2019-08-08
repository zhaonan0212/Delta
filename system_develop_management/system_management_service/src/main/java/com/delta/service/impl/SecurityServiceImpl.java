package com.delta.service.impl;

import com.delta.dao.UserDao;
import com.delta.domain.SysDevelopManaRole;
import com.delta.domain.SysDevelopManaUser;
import com.delta.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value ="securityServiceImpl")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //首先查詢用戶信息
        SysDevelopManaUser sysUser = userDao.findByUserName(username);
        System.out.println(sysUser);
        //判斷用戶是否存在
        if (sysUser != null) {
            // 先设置假的权限
            List<GrantedAuthority> authorities = new ArrayList<>();
            //獲取用火角色
            List<SysDevelopManaRole> roles = sysUser.getRoles();
            for (SysDevelopManaRole role : roles) {
                //傳入每一個角色
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            // 创建用户
            User user = new User(username, sysUser.getPassword(), authorities);
            return user;
        }
        return null;
    }
}
