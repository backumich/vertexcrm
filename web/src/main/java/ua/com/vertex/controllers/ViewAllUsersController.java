package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;

@Controller
@RequestMapping(value = "/viewAllUsers")
public class ViewAllUsersController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "viewAllUsers";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private UserLogic userLogic;

    @Autowired
    public ViewAllUsersController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @GetMapping
    public ModelAndView viewAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName(PAGE_JSP);
            List<User> users = userLogic.getAllUsers();
            modelAndView.addObject("users", users);
            LOGGER.debug("Received a list of all users and transferred to the model");
            String allUsersEmail = "";
            for (User user : users) {
                allUsersEmail += user.getEmail() + "|";
            }
            LOGGER.debug("Quantity users -" + users.size());
            LOGGER.debug("All users list -" + allUsersEmail);
        } catch (Exception e) {
            LOGGER.warn(e);
            modelAndView.setViewName(ERROR_JSP);
        }
        return modelAndView;
    }
}

