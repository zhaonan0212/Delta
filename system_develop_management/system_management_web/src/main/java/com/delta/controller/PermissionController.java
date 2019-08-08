package com.delta.controller;

import com.delta.domain.SysDevelopManaPermission;
import com.delta.service.PermissionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/permission")
public class PermissionController {


    @Autowired
    private PermissionService permissionService;

    /*@RequestMapping("/findAll")
    public String findAll(Model model) {
        List<SysDevelopManaPermission> permissionList = permissionService.findAll();
        model.addAttribute("permissionList", permissionList);
        System.out.println(permissionList);
        return "permission-list";
    }*/

    /**
     * 分頁查詢
     * @param model 模型視圖轉化
     * @param page 當前第幾頁
     * @param size 每頁多少條
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(Model model, @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                          @RequestParam(value = "size",defaultValue = "10",required = false)int size){
        PageInfo<SysDevelopManaPermission> pageInfo = permissionService.pageHelperList(page,size);
        model.addAttribute("pageInfo",pageInfo);
        return "permission-list";
    }

    /**
     * 跳轉到添加頁
     * @return
     */
    @RequestMapping(value ="/add",method = RequestMethod.GET)
    public String add(){
        return "permission-add";
    }

    /**
     * 添加參數
     * @param permission
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(SysDevelopManaPermission permission){
        int i = permissionService.add(permission);
        return "redirect:/permission/findAll";

    }

    /**
     * 刪除數據
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        int i =permissionService.delete(id);
        return "redirect:/permission/findAll";
    }

    /**
     * 根據id查詢
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/findById")
    public String findById(Integer id,Model model){
        SysDevelopManaPermission permission = permissionService.findById(id);
        model.addAttribute("permission",permission);
        return "permission-update";
    }

    /**
     * 更新
     * @param permission
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(SysDevelopManaPermission permission){
        int i =permissionService.update(permission);
        return "redirect:/permission/findAll";
    }
}
