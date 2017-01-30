package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;

@Controller
//@RequestMapping(value = "/userDetails")
@SessionAttributes("users")
public class UserDetailsController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "userDetails";

    private UserLogic userLogic;

    @Autowired
    public UserDetailsController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    @RequestMapping(value = "/userDetails")
    public ModelAndView getUserDetailsByID(@RequestParam("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();
        User user = null;
        try {
            user = userLogic.getUserDetailsByID(userId);
            LOGGER.debug("Get full data for user ID - " + userId);
        } catch (DataAccessException | SQLException e) {
            LOGGER.debug("During preparation the all data for user ID - " + userId + " there was a database error");
            modelAndView.setViewName(ERROR_JSP);
        }
        if (user != null) {
            modelAndView.setViewName(PAGE_JSP);
            modelAndView.addObject("user", user);
            try {
                modelAndView.addObject("imagePassportScan", userLogic.convertImage(user.getPassportScan()));
                LOGGER.debug("Passports scan is obtained and converted for user ID - " + userId);
            } catch (Throwable t) {
                LOGGER.debug("There are problems with access to passports scan for user ID - " + userId);
            }
            try {
                modelAndView.addObject("imagePhoto", userLogic.convertImage(user.getPhoto()));
                LOGGER.debug("Photo is obtained and converted for user ID - " + userId);
            } catch (Throwable t) {
                LOGGER.debug("There are problems with access to photos for user ID - " + userId);
            }
        }
        return modelAndView;
    }

//    @RequestMapping(value = {"/saveUserData"}, method = RequestMethod.POST)
//    public String saveEmployee(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model) {
//
//        int a = 5;
//        if (result.hasErrors()) {
//
//            return "registration";
//        }
//
//
//        model.addAttribute("success", "Employee " + user.getEmail() + " registered successfully");
//        return "success";
//    }
}

