package com.delta.controller;

import com.delta.domain.SysDevelopManaRole;
import com.delta.domain.SysDevelopManaUser;
import com.delta.service.RoleService;
import com.delta.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private RoleService roleService;

   /* @RequestMapping("/findAll")
    public String findAll(Model model){
        List<SysDevelopManaUser> userList = userService.findAll();
        model.addAttribute("userList",userList);

        System.out.println(userList);
        return "user-list";
    }*/

    /**
     * 分頁查詢
     *
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageInfo<SysDevelopManaUser> pageInfo = userService.pageHelperList(page, size);
        model.addAttribute(pageInfo);
        return "user-list";
    }

    /**
     * 添加用戶
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(SysDevelopManaUser user) {
        //設置密碼BCryptPasswordEncoder
        user.setPassword(encoder.encode(user.getPassword()));
        int i = userService.add(user);
        return "redirect:/user/findAll";
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(Integer id, Model model) {
        SysDevelopManaUser user = userService.findById(id);
        model.addAttribute("user", user);
        System.out.println(user);
        return "user-show";
    }

    /**
     * 刪除用戶
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id) {
        userService.delete(id);
        return "redirect:/user/findAll";
    }

    /**
     * 模糊搜索
     *
     * @param model
     * @param page
     * @param size
     * @param xname
     * @return
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.GET)
    public String searchByName(Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size, String xname) {
        PageInfo<SysDevelopManaUser> pageInfo = userService.searchByName(page, size, xname);
        System.out.println(pageInfo);
        model.addAttribute(pageInfo);
        return "user-list";
    }

    /**
     * 查詢用戶的角色
     * @param userId
     * @return
     */
    @RequestMapping(value = "/findRoleByUser",method = RequestMethod.GET)
    public String findRoleByUser(Model model,Integer userId) {
        //查詢當前用戶和權限
        SysDevelopManaUser user = userService.findById(userId);
        List<SysDevelopManaRole> roles = user.getRoles();
        StringBuffer stringBuffer = new StringBuffer();
        if (roles != null && roles.size() > 0) {
            for (SysDevelopManaRole role : roles) {
                stringBuffer.append(role.getRoleName());
            }
        }

        //全部權限
        List<SysDevelopManaRole> roleList = roleService.findAll();
        //所有權限的拼接
        model.addAttribute("roleNames",stringBuffer.toString());
        //用戶id
        model.addAttribute("user", user);
        //所有權限
        model.addAttribute("roleList", roleList);
        return "user-role-add";
    }


    /**
     * 保存修改用戶角色
     * @param userId
     * @param ids
     * @return
     */
    @RequestMapping(value = "/addRoleToUser",method = RequestMethod.POST)
    public String addRoleToUser(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "ids") List<Integer> ids){
        int i  = userService.addRoleToUser(userId,ids);
        return "redirect:/user/findAll";
    }


}
