package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/viewAllUsers")
@SessionAttributes(value = "viewAllUsers")
public class ViewAllUsersController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "viewAllUsers";

    private static final Logger LOGGER = LogManager.getLogger(ViewAllUsersController.class);

    private UserLogic userLogic;

    @GetMapping
    public ModelAndView viewAllUsers(@ModelAttribute DataNavigator dataNavigator) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<User> users = userLogic.getUsersPerPages(dataNavigator);
            modelAndView.addObject("viewAllUsers", dataNavigator);
            modelAndView.addObject("users", users);
            modelAndView.setViewName(PAGE_JSP);
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

    @PostMapping
    public ModelAndView reload(@ModelAttribute DataNavigator dataNavigator) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<User> users = userLogic.getUsersPerPages(dataNavigator);
            modelAndView.addObject("users", users);
            modelAndView.addObject("viewAllUsers", dataNavigator);
            modelAndView.setViewName(PAGE_JSP);
            LOGGER.debug("Received a list of all users and transferred to the model");
//            String allUsersEmail = "";
//            for (User user : users) {
//                allUsersEmail += user.getEmail() + "|";
//            }
            String allUsersEmail = users.stream().map(User::getEmail).collect(Collectors.joining("|"));

            LOGGER.debug("Quantity users -" + users.size());
            LOGGER.debug("All users list -" + allUsersEmail);
        } catch (Exception e) {
            LOGGER.warn(e);
            modelAndView.setViewName(ERROR_JSP);
        }
        return modelAndView;
    }

    @ModelAttribute
    public DataNavigator createDataNavigator() {
        return new DataNavigator("viewAllUsers");
    }

    @Autowired
    public ViewAllUsersController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}

