package ua.com.vertex.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public ModelAndView welcome() {
        return new ModelAndView("redirect:/welcome.jsp");
    }
}
