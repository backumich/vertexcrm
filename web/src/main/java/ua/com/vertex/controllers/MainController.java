package ua.com.vertex.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static int count = 0;

    @RequestMapping("/welcome")
    public ModelAndView welcome() {
        String message = String.format("Welcome, you are %d today", ++count);
        ModelAndView result = new ModelAndView("redirect:/welcome.jsp", "message", message);
        return result;
    }
}
