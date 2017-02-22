package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;
import ua.com.vertex.utils.MailService;

@Controller
@RequestMapping(value = "/1registration")
public class ActivationUserController {

    static final String REGISTRATION_PAGE = "registration";
    private static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
    private static final String REGISTRATION_ERROR_PAGE = "registrationError";
    private static final String NAME_USER_MODEL_FOR_REGISTRATION_PAGE = "userFormRegistration";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private RegistrationUserLogic registrationUserLogic;

    @Autowired
    public ActivationUserController(RegistrationUserLogic registrationUserLogic) {
        this.registrationUserLogic = registrationUserLogic;
    }

    @Autowired
    private MailService mailService;

    @GetMapping
    public ModelAndView viewRegistrationForm() {
        LOGGER.info("First request to " + REGISTRATION_PAGE);
        return new ModelAndView(REGISTRATION_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, new UserFormRegistration());
    }
}

