package ua.com.vertex.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.UserLogic;

@Controller
@RequestMapping("/home")
@SessionAttributes("userIds")
public class HomePageController {

    @Autowired
    private UserLogic userLogic;

    @GetMapping
    public ModelAndView showHomePage() {
        ModelAndView result = new ModelAndView("redirect:home.jsp");
        result.addObject("userIds", userLogic.getAllUserIds());

        return result;

    }
}
