package com.example.mobilele.web;

import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.entity.User;
import com.example.mobilele.models.entity.UserRole;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {


    @GetMapping()
    public ModelAndView home(ModelAndView model){
        model.setViewName("index");
        return model;
    }

}
