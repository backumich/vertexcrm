package ua.com.vertex.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private static final String REGISTRATION_PAGE = "registration";
    private static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
    private static final String REGISTRATION_ERROR_PAGE = "registrationError";
    private static final String NAME_USER_MODEL_FOR_REGISTRATION_PAGE = "userFormRegistration";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private RegistrationUserLogic registrationUserLogic;

    public RegistrationController() {
    }

    @Autowired
    public RegistrationController(RegistrationUserLogic registrationUserLogic) {
        this.registrationUserLogic = registrationUserLogic;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewRegistrationForm() {
        LOGGER.info("First request to registration.jsp, " +
                "create model 'UserFormRegistration' and send ModelAndView to registration.jsp");
        return new ModelAndView(REGISTRATION_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, new UserFormRegistration());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processRegistration(@Valid @ModelAttribute
            (NAME_USER_MODEL_FOR_REGISTRATION_PAGE) UserFormRegistration userFormRegistration,
                                            BindingResult bindingResult, ModelAndView modelAndView) {

        if (registrationUserLogic.checkEmailAlreadyExists(userFormRegistration) != 0) {
            LOGGER.info("Check for signed-in user on a page registration.jsp");
            bindingResult.rejectValue("email", "error.email", "User with that email is already registered!");
        }

        if (!registrationUserLogic.isMatchPassword(userFormRegistration)) {
            LOGGER.info("check for errors associated with the coincidence of the password on page registration.jsp");
            bindingResult.rejectValue("verifyPassword", "error.verifyPassword", "Passwords do not match!");
        }

        if (bindingResult.hasErrors()) {
            LOGGER.info("There are errors in filling in the form registration.jsp");
            modelAndView.setViewName(REGISTRATION_PAGE);
            modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
            return modelAndView;
        }

        userFormRegistration = registrationUserLogic.encryptPassword(userFormRegistration);

        User user = registrationUserLogic.userFormRegistrationToUser(userFormRegistration);

        try {
            int userID = registrationUserLogic.registrationUser(user);
            modelAndView.addObject("userID", userID);
            modelAndView.setViewName(REGISTRATION_SUCCESS_PAGE);
            modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
        } catch (RuntimeException e) {
            return new ModelAndView(REGISTRATION_ERROR_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
        }

        return modelAndView;
        //return new ModelAndView(REGISTRATION_SUCCESS_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
    }
}

