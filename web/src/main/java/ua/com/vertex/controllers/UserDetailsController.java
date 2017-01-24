package ua.com.vertex.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/userDetails")
@SessionAttributes("users")
public class UserDetailsController {
    private static final String ERROR_JSP = "error";

    UserLogic userLogic;

    @Autowired
    public UserDetailsController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    public ModelAndView getUserDetailsByID(@RequestParam("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();
        User user = null;
        try {
            user = userLogic.getUserDetailsByID(userId);
        } catch (SQLException e) {
            modelAndView.setViewName(ERROR_JSP);
        }
        if (user != null) {
            modelAndView.setViewName("userDetails");
            modelAndView.addObject("user", user);

            try {
                String encodedImagePassportScan = Base64.encode(user.getPassportScan());
                modelAndView.addObject("imagePassportScan", encodedImagePassportScan);
                LOGGER.debug("Passports scan is obtained and converted");
            } catch (Throwable t) {
                LOGGER.debug("There are problems with access to passports scan");
            }
            try {
                String encodedPhoto = Base64.encode(user.getPhoto());
                modelAndView.addObject("imagePhoto", encodedPhoto);
                LOGGER.debug("Photo is obtained and converted");
            } catch (Throwable t) {
                LOGGER.debug("There are problems with access to photos");
            }
        }
        //todo:проверить работу исключения

//        else {
//            modelAndView.setViewName(ERROR_JSP);
//        }
        return modelAndView;
    }
}

