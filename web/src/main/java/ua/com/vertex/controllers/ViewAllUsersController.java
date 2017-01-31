package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "/viewAllUsers")
@SessionAttributes("users")
public class ViewAllUsersController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "viewAllUsers";

    private UserLogic userLogic;

    @Autowired
    public ViewAllUsersController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    public ModelAndView viewAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName(PAGE_JSP);
            modelAndView.addObject("users", userLogic.getListUsers());
            LOGGER.debug("Get list all users");
        } catch (DataAccessException | SQLException e) {
            LOGGER.debug("During preparation the list of users there was a database error");
            modelAndView.setViewName(ERROR_JSP);
        }
        return modelAndView;
    }
}

