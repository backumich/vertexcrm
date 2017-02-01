package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/saveUserData")
public class SaveUserDataController {

    static final String USER_DETAILS_PAGE = "userDetails";
    private static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
    private static final String REGISTRATION_ERROR_PAGE = "registrationError";
    private static final String USERDATA_MODEL_FOR_SAVE = "user";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private UserLogic userLogic;

    @Autowired
    public SaveUserDataController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping
    public ModelAndView saveUserData(@Valid @RequestParam("roleId") int role, @ModelAttribute(USERDATA_MODEL_FOR_SAVE)
            User user, BindingResult bindingResult, ModelAndView modelAndView) {
//    public ModelAndView saveUserData(@Valid @ModelAttribute(USERDATA_MODEL_FOR_SAVE)
//                                             User user, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(USER_DETAILS_PAGE);
            modelAndView.addObject(USERDATA_MODEL_FOR_SAVE, user);
        } else {
            modelAndView.setViewName(USER_DETAILS_PAGE);
            modelAndView.addObject(USERDATA_MODEL_FOR_SAVE, user);

        }

        return modelAndView;
    }
}

