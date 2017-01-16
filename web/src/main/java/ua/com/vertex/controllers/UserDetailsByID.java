package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.ViewAllUsersLogic;

@Controller
@RequestMapping(value = "/getUserDetailsByID")
@SessionAttributes("users")
public class UserDetailsByID {

    ViewAllUsersLogic viewAllUsersLogic;

    @Autowired
    public UserDetailsByID(ViewAllUsersLogic viewAllUsersLogic) {
        this.viewAllUsersLogic = viewAllUsersLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @PostMapping
    //public ModelAndView getUserDetailsByID(int userID) {
    public ModelAndView getUserDetailsByID(@RequestParam("userID") int userID) {
        int userIDq = userID;
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;

    }
}

