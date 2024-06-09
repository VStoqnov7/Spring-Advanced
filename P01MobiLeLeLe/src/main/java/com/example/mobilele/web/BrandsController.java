package com.example.mobilele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/brands")
public class BrandsController {


    @GetMapping("/all")
    public ModelAndView allBrands(ModelAndView model){
        model.setViewName("brands");
        return model;
    }
}
