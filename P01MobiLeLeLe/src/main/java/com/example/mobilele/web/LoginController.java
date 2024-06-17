package com.example.mobilele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/user")
public class LoginController {
    /* private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }*/

    /*
    @ModelAttribute
    public UserLoginDTO loginDTO() {
        return new UserLoginDTO();
    }*/

    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("auth-login");
        return model;
    }

    @PostMapping("/login-error")
    public ModelAndView onLoginFailure(ModelAndView modelAndView, @ModelAttribute("username") String username) {

        modelAndView.addObject("bad_credentials", true);
        modelAndView.setViewName("redirect:/auth-login");
        return modelAndView;
    }

   /* @PostMapping("/login")
    public ModelAndView login(ModelAndView model,
                                 @Valid UserLoginDTO userLoginDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.setViewName("auth-login");
            model.addObject("errorMessage", true);
            return model;
        }
        this.userService.loginUser(userLoginDTO);
        model.setViewName("redirect:/home");
        return model;
    }*/
}
