package com.delta.controller;

import com.delta.service.SystemCateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/systemCat")
public class SystemCatController {

    @Autowired
    private SystemCateService systemCateService;

    @RequestMapping(value = "/findByNum",method = RequestMethod.GET)
    @ResponseBody
    public void findByNum(HttpServletRequest request, HttpServletResponse response){
        //解決亂碼問題
        response.setContentType("text/html;charset=UTF-8");

        try {
            String number = request.getParameter("number");
            String content = systemCateService.findByNum(number);
            //轉換
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(content);
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
