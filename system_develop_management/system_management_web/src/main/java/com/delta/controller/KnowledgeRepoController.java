package com.delta.controller;

import com.delta.EchartResult;
import com.delta.domain.KnowledgeRepository;
import com.delta.service.KnowledgeRepoService;
import com.delta.PageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/knowledge_repo")
public class KnowledgeRepoController {

    @Autowired
    private KnowledgeRepoService knowledgeRepoService;

    /*  *//**
     * 查詢全部
     * @param model
     * @return
     *//*
    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<KnowledgeRepository> knowledgeRepoList = knowledgeRepoService.findAll();
        model.addAttribute("knowledgeRepoList",knowledgeRepoList);
        System.out.println(knowledgeRepoList);
        return "knowledge-list";
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
                          @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PageInfo<KnowledgeRepository> pageInfo = knowledgeRepoService.pageHelperList(page, size);
        model.addAttribute("pageInfo", pageInfo);
        return "knowledge-list";
    }

    /**
     * 新建
     *
     * @param knowledgeRepository
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(KnowledgeRepository knowledgeRepository) {
        int c = knowledgeRepoService.add(knowledgeRepository);
        return "redirect:/knowledge_repo/findAll";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "knowledge-add";
    }

    /**
     * 根據id查詢，展示
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(Integer id, Model model) {
        KnowledgeRepository knowledgeRepo = knowledgeRepoService.findById(id);
        model.addAttribute("knowledgeRepo", knowledgeRepo);
        return "knowledge-update";
    }

    /**
     * 更新內容
     *
     * @param knowledgeRepository
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(KnowledgeRepository knowledgeRepository) {
        int a = knowledgeRepoService.update(knowledgeRepository);
        return "redirect:/knowledge_repo/findAll";
    }

    /**
     * 刪除內容
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id) {
        knowledgeRepoService.delete(id);
        return "redirect:/knowledge_repo/findAll";
    }

    /**
     * 搜索功能，模糊查詢
     *
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.GET)
    public String searchByName(Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(value = "size", defaultValue = "10", required = false) int size, String xname) {
        PageInfo<KnowledgeRepository> pageInfo = knowledgeRepoService.searchByName(page, size, xname);
        model.addAttribute("pageInfo", pageInfo);
        return "knowledge-search";
    }

    /**
     * 傳輸據給echarts來顯示
     * @param request
     * @param response
     */
    @RequestMapping("/echarts")
    @ResponseBody
    public void echarts(HttpServletRequest request, HttpServletResponse response) {

        //輸出亂碼
        response.setContentType("text/html;charset=UTF-8");

        try {
            //輸入亂碼
            request.setCharacterEncoding("UTF-8");
            List<EchartResult>  exceptionList = knowledgeRepoService.echarts();
            System.out.println(exceptionList);

            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(exceptionList);
            System.out.println(data);
            response.getWriter().print(data);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }}
