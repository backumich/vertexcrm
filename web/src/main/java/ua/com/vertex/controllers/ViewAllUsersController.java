package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;

@Controller
@SessionAttributes(value = "viewAllUsers")
public class ViewAllUsersController {
    private static final String PAGE_JSP = "viewAllUsers";

    private static final Logger LOGGER = LogManager.getLogger(ViewAllUsersController.class);

    private UserLogic userLogic;

    @RequestMapping(value = "/viewAllUsers")
    public ModelAndView viewAllUsers(@ModelAttribute DataNavigator dataNavigator) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        int quantityUsers = userLogic.getQuantityUsers();
        dataNavigator = DataNavigator.updateDataNavigator(dataNavigator, quantityUsers);
        List<User> users = userLogic.getUsersPerPages(dataNavigator);

        modelAndView.addObject("viewAllUsers", dataNavigator);
        modelAndView.addObject("users", users);
        modelAndView.setViewName(PAGE_JSP);
        LOGGER.debug("Received a list of all users and transferred to the model");

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

