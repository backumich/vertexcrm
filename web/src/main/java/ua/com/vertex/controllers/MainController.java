package ua.com.vertex.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static int count = 0;

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public ModelAndView welcome() {
        //todo: this seems to be redundant too
        String message = String.format("Welcome, you are %d today", ++count);
        return new ModelAndView("redirect:/welcome.jsp", "message", message);
    }
}
