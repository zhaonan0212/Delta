package com.delta.controller;


import com.delta.EchartResult;
import com.delta.domain.ApiModule;
import com.delta.service.ApiModuleService;
import com.delta.PageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/api_mod")
public class ApiModuleController {

    @Autowired
    private ApiModuleService apiModuleService;

   /* *//**
     * 查詢全部
     * @param model
     * @return
     *//*
    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<ApiModule> apiModuleList = apiModuleService.findAll();
        model.addAttribute("apiModuleList",apiModuleList);
        System.out.println(apiModuleList);
        return "api-list";
    }*/

    /**
     * 分頁查詢
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(Model model, @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                          @RequestParam(value = "size",defaultValue = "10",required = false)int size){
        PageInfo<ApiModule> pageInfo = apiModuleService.pageHelperList(page,size);
        System.out.println(pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        return "api-list";
    }

    /**
     * 跳轉到添加頁
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return "api-add";
    }

    /**
     * 添加
     * @param apiModule
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(ApiModule apiModule){
        int a = apiModuleService.add(apiModule);
        return "redirect:/api_mod/findAll";
    }

    /**
     * 根據id查詢
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public String findById(Integer id,Model model){
        ApiModule apiModule = apiModuleService.findById(id);
        model.addAttribute("apiModule",apiModule);
        return "api-update";
    }

    /**
     * 更新內容
     * @param apiModule
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String  update(ApiModule apiModule){
        int a = apiModuleService.update(apiModule);
        return "redirect:/api_mod/findAll";
    }

    /**
     * 刪除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(int id){
        apiModuleService.delete(id);
        return "redirect:/api_mod/findAll";
    }


    /**
     * 搜索功能，模糊查詢
     * @param model
     * @param page
     * @param size
     * @param xname
     * @return
     */
    @RequestMapping(value = "/searchByName",method = RequestMethod.GET)
    public String searchByName(Model model, @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                          @RequestParam(value = "size",defaultValue = "10",required = false)int size,String xname){
        PageInfo<ApiModule> pageInfo = apiModuleService.searchByName(page,size,xname);
        pageInfo.setParams(xname);
        model.addAttribute("pageInfo",pageInfo);
        return "api-search";
    }

    /**
     * 返回給echart的數據
     * @return
     */
    @RequestMapping("/echart")
    @ResponseBody
    public void echart(HttpServletResponse response, HttpServletRequest request){

        response.setContentType("html/test;charset=UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
            List<EchartResult>  apiList = apiModuleService.echart();

            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(apiList);
            response.getWriter().print(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
