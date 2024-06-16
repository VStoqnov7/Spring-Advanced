package com.example.mobilele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SessionController {

    @GetMapping("/session-timeout")
    public ModelAndView sessionTimeout(ModelAndView model) {
        model.setViewName("session-timeout.html");
        return model;
    }

    @GetMapping("/session-expired")
    public ModelAndView sessionExpired(ModelAndView model) {
        model.setViewName("session-expired");
        return model;
    }
}