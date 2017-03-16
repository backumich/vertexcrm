package ua.com.vertex.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/home")
@SessionAttributes("userIds")
public class HomePageController {


    @GetMapping
    public ModelAndView showHomePage() {
        return new ModelAndView("redirect:home.jsp");
    }
}
