package ua.com.vertex.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private RegistrationUserLogic registrationUserLogic;

    //to-do: what for?
//    public RegistrationController() {
//    }

    @Autowired
    public RegistrationController(RegistrationUserLogic registrationUserLogic) {
        this.registrationUserLogic = registrationUserLogic;
    }

    //to-do: you can use @GetMapping
    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ModelAndView viewRegistrationForm() {
        //to-do: ("First request to " + this.REGISTRATION_PAGE) would be enough
        //LOGGER.info("First request to registration.jsp, create model 'UserFormRegistration' and send ModelAndView to registration.jsp");
        LOGGER.debug("First request to " + REGISTRATION_PAGE);
        return new ModelAndView(REGISTRATION_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, new UserFormRegistration());
    }

    //to-do: @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    //to-do: something wrong with your formatting
    public ModelAndView processRegistration(@Valid @ModelAttribute(NAME_USER_MODEL_FOR_REGISTRATION_PAGE)
                                                    UserFormRegistration userFormRegistration, BindingResult bindingResult, ModelAndView modelAndView) {

        //todo: refactor it

        isMatchPassword(userFormRegistration, bindingResult);
        checkEmailAlreadyExists(userFormRegistration.getEmail(), bindingResult);

        if (bindingResult.hasErrors()) {
            LOGGER.info("There are errors in filling in the form " + REGISTRATION_PAGE);
            modelAndView.setViewName(REGISTRATION_PAGE);
            //modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
            //return modelAndView;
        } else {

            //todo: this action should be included into registration logic
            userFormRegistration = registrationUserLogic.encryptPassword(userFormRegistration);

            //todo: you can add constructor to User-class.
            User user = registrationUserLogic.userFormRegistrationToUser(userFormRegistration);

            try {
                int userID = registrationUserLogic.registrationUser(user);
                modelAndView.addObject("userID", userID);
                modelAndView.setViewName(REGISTRATION_SUCCESS_PAGE);
                //modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
            } catch (DataAccessException e) {
                modelAndView.setViewName(REGISTRATION_ERROR_PAGE);
                //modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
                //return new ModelAndView(REGISTRATION_ERROR_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
                //return modelAndView;
            }
        }
        modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
        return modelAndView;
        //return new ModelAndView(REGISTRATION_SUCCESS_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
    }

    //to-do: what are these annotations for?
    private void isMatchPassword(UserFormRegistration userFormRegistration, BindingResult bindingResult) {
        if (!registrationUserLogic.isMatchPassword(userFormRegistration)) {
//            to-do: check for errors --- it is already checked at this moment.
            //LOGGER.info("check for errors associated with the coincidence of the password on page registration.jsp");
            LOGGER.debug("when a user registration " + userFormRegistration.getEmail() + " were entered passwords do not match");
            bindingResult.rejectValue("verifyPassword", "error.verifyPassword", "Passwords do not match!");
        }
    }

//    private void checkEmailAlreadyExists(UserFormRegistration userFormRegistration, BindingResult bindingResult) {
//        //to-do: email would be enough as parameter
//        if (registrationUserLogic.checkEmailAlreadyExists(userFormRegistration) != 0) {
//            //to-do: it is already known if such user exists at this moment
//            //LOGGER.info("Check for signed-in user on a page registration.jsp");
//            LOGGER.info("That email |" + userFormRegistration.getEmail() + "| is already registered");
//            bindingResult.rejectValue("email", "error.email", "User with that email is already registered!");
//        }
//    }

    private void checkEmailAlreadyExists(String email, BindingResult bindingResult) {
        //to-do: email would be enough as parameter
        if (registrationUserLogic.checkEmailAlreadyExists(email)) {
            //to-do: it is already known if such user exists at this moment
            //LOGGER.info("Check for signed-in user on a page registration.jsp");
            LOGGER.info("That email |" + email + "| is already registered");
            bindingResult.rejectValue("email", "error.email", "User with that email is already registered!");
        }
    }
}

