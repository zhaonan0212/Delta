package com.delta.controller;

import com.delta.service.KnowledgeCateService;
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
@RequestMapping("/knowledgeCate")
public class KnowledgeCateController {

    @Autowired
    private KnowledgeCateService knowledgeCateService;

    @RequestMapping(value = "/findByKid",method = RequestMethod.GET)
    @ResponseBody
    public void findByKid(HttpServletResponse response, HttpServletRequest request){
        response.setContentType("text/html;charset=UTF-8");

        try {
            String kid = request.getParameter("kid");
            String category = knowledgeCateService.findByKid(kid);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(category);
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
