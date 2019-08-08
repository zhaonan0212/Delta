package com.delta.controller;

import com.delta.domain.SysDevelopManaPermission;
import com.delta.domain.SysDevelopManaRole;
import com.delta.service.PermissionService;
import com.delta.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<SysDevelopManaRole> roleList = roleService.findAll();
        model.addAttribute("roleList", roleList);
        System.out.println(roleList);
        return "role-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(SysDevelopManaRole role) {
        int i = roleService.add(role);
        return "redirect:/role/findAll";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(Integer id) {
        int i = roleService.delete(id);
        return "redirect:/role/findAll";
    }

  /*  @RequestMapping("/addPermissionToRole")
    public  String addPermissionToRole(Integer id){
        int i =roleService.addPermissionToRole(id);
        return
    }*/

    /**
     * 頁面展示全部權限，擁有的前面會打勾
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/showPermission", method = RequestMethod.GET)
    public String showPermission(Integer roleId, Model model) {
        SysDevelopManaRole role = roleService.findByRoleId(roleId);
        List<SysDevelopManaPermission> permissions = role.getPermissions();
        StringBuffer stringBuffer = new StringBuffer();
        if (permissions != null && permissions.size() > 0) {
            for (SysDevelopManaPermission permission : permissions) {
                stringBuffer.append(permission);
            }
        }
        System.out.println("=====");
        System.out.println(stringBuffer.toString());
        List<SysDevelopManaPermission> per = permissionService.findAll();
        model.addAttribute("permissionName", stringBuffer.toString());
        model.addAttribute("role", role);
        model.addAttribute("permissionList", per);
        return "role-permission-add";
    }

    /**
     * 修改權限功能
     * @param roleId
     * @param ids
     * @return
     */
    @RequestMapping(value = "/addPermissionToRole",method = RequestMethod.POST)
    public String addPermissionToRole(@RequestParam("roleId") Integer roleId,@RequestParam("ids") List<Integer> ids) {
        int i  = roleService.addPermissionToRole(roleId,ids);
        return "redirect:/role/findAll";

    }
}
