package com.example.mobilele.web;

import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user/brands")
public class BrandsController {
    private final BrandService brandService;

    public BrandsController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public ModelAndView allBrands(ModelAndView model){
        List<Brand> allBrands = this.brandService.findAll();
        model.addObject("allBrands",allBrands);
        model.setViewName("brands");
        return model;
    }
}
