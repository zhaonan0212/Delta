package com.delta.controller;


import com.delta.EchartResult;
import com.delta.domain.DataDictionary;
import com.delta.service.DataDicService;
import com.delta.PageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
@RequestMapping("/data_dic")
public class DataDicController {

    @Autowired
    private DataDicService dataDicService;

    /**
     * 查詢全部
     * @return
     *//*
    @RequestMapping("/findAll")
    public String findAll(Model model){
         List<DataDictionary> dataList = dataDicService.findAll();
         System.out.println(dataList);
         model.addAttribute("dataList",dataList);
         return  "data-list";
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
        PageInfo<DataDictionary> pageInfo =  dataDicService.pageHelperList(page,size);
        model.addAttribute("pageInfo",pageInfo);
        return "data-list";
    }

    /**
     * 根據id查詢,顯示
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public  String   findById(Integer id,Model model){
        DataDictionary dataBase = dataDicService.findById(id);
        model.addAttribute("dataBase",dataBase);
        return "data-update";
    }

    /**
     * 修改
     * @param dataDictionary
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(DataDictionary dataDictionary){
        int a = dataDicService.update(dataDictionary);
        return "redirect:/data_dic/findAll";

    }

    /**
     * 新建
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public  String add(){
        return "data-add";
    }


    /**
     * 保存
     * @param dataDictionary
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(DataDictionary dataDictionary){
       int i =dataDicService.add(dataDictionary);
       return "redirect:/data_dic/findAll";
    }


    /**
     * 刪除
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
       int i = dataDicService.delete(id);
       return "redirect:/data_dic/findAll";

    }

    /**
     * 模糊搜索
     * @param model
     * @param page
     * @param size
     * @param xname
     * @return
     */
    @RequestMapping(value = "/searchByName",method = RequestMethod.GET)
    public String searchByName(Model model, @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                               @RequestParam(value = "size",required = false,defaultValue = "10")int size,String xname){
        PageInfo<DataDictionary> pageInfo = dataDicService.searchByName(page,size,xname);
        System.out.println(pageInfo);
        model.addAttribute(pageInfo);
        return "data-search";
    }

    /**
     * 返回給echart數據
     * @param request
     * @param response
     */
    @RequestMapping("/echart")
    @ResponseBody
    public void echart(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("html/text;charset=utf-8");


        try {
            request.setCharacterEncoding("UTF-8");

            List<EchartResult> dataList = dataDicService.echart();

            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(dataList);
            response.getWriter().print(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
