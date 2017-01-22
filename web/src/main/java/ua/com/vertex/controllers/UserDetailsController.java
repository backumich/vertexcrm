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

@Controller
@RequestMapping(value = "/userDetails")
@SessionAttributes("users")
public class UserDetailsController {

    UserLogic userLogic;

    @Autowired
    public UserDetailsController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping
    public ModelAndView getUserDetailsByID(@RequestParam("userID") int userID) {
        User user = userLogic.getUserDetailsByID(userID);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userDetails");
        modelAndView.addObject("user", user);
        String encodedImagePassportScan = Base64.encode(user.getPassportScan());
        modelAndView.addObject("imagePassportScan", encodedImagePassportScan);
        String encodedPhoto = Base64.encode(user.getPhoto());
        modelAndView.addObject("imagePhoto", encodedPhoto);
        return modelAndView;

    }
}

