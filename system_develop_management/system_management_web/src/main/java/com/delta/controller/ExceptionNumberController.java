package com.delta.controller;



import com.delta.service.ExceptionNumberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/exception_num")
public class ExceptionNumberController {

    @Autowired
    private ExceptionNumberService exceptionNumberService;

    @RequestMapping("/findByNum")
    @ResponseBody
    public void findByNum(HttpServletRequest request, HttpServletResponse response){
        //輸出亂碼
        response.setContentType("text/html;charset=UTF-8");

        try {
            //輸入亂碼
            request.setCharacterEncoding("UTF-8");
            String num = request.getParameter("num");
            String content = exceptionNumberService.findByNum(num);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(content);
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
