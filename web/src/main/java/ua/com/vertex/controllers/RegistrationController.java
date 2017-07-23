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
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;
import ua.com.vertex.utils.MailService;

import javax.validation.Valid;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    static final String REGISTRATION_PAGE = "registration";
    static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
    static final String REGISTRATION_ERROR_PAGE = "registrationError";
    static final String NAME_MODEL = "userFormRegistration";
    private static final String OUR_EMAIL = "vertex.academy.robot@gmail.com";
    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);
    private final MailService mailService;
    private RegistrationUserLogic registrationUserLogic;
    private EmailLogic emailLogic;

    @GetMapping
    public ModelAndView viewRegistrationForm() {
        LOGGER.info("Get page - " + REGISTRATION_PAGE);
        return new ModelAndView(REGISTRATION_PAGE, NAME_MODEL, new UserFormRegistration());
    }

    @PostMapping
    public ModelAndView processRegistration(@Valid @ModelAttribute(NAME_MODEL)
                                                    UserFormRegistration userFormRegistration,
                                            BindingResult bindingResult, ModelAndView modelAndView) {

        LOGGER.debug("Request to /processRegistration by " + userFormRegistration.getEmail());
        modelAndView.setViewName(REGISTRATION_PAGE);
        if (!bindingResult.hasErrors()) {
            try {
                if (registrationUserLogic.isRegisteredUser(userFormRegistration, bindingResult)) {
                    modelAndView.setViewName(REGISTRATION_SUCCESS_PAGE);
                    mailService.sendMail(OUR_EMAIL, userFormRegistration.getEmail(),
                            "Confirmation of registration",
                            emailLogic.createRegistrationMessage(userFormRegistration));
                }
            } catch (DataAccessException e) {
                modelAndView.setViewName(REGISTRATION_ERROR_PAGE);
                LOGGER.warn(e);
            } catch (Exception e) {
                modelAndView.setViewName(ERROR);
                LOGGER.warn(e);
            }
        }

        return modelAndView;
    }

    @Autowired
    public RegistrationController(RegistrationUserLogic registrationUserLogic,
                                  EmailLogic emailLogic, MailService mailService) {
        this.registrationUserLogic = registrationUserLogic;
        this.emailLogic = emailLogic;
        this.mailService = mailService;
    }
}