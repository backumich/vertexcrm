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
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.MailService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    static final String REGISTRATION_PAGE = "registration";
    private static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
    private static final String REGISTRATION_ERROR_PAGE = "registrationError";
    private static final String NAME_USER_MODEL_FOR_REGISTRATION_PAGE = "userFormRegistration";
    private static final String ENCRYPT_KEY = "VeRtEx AcAdeMy";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private RegistrationUserLogic registrationUserLogic;

    private UserLogic userLogic;

    @Autowired
    public RegistrationController(RegistrationUserLogic registrationUserLogic, UserLogic userLogic, UserLogic userLogic1) {
        this.registrationUserLogic = registrationUserLogic;
        this.userLogic = userLogic1;
    }

    @Autowired
    private MailService mailService;

    @GetMapping
    public ModelAndView viewRegistrationForm() {
        LOGGER.info("First request to " + REGISTRATION_PAGE);
        return new ModelAndView(REGISTRATION_PAGE, NAME_USER_MODEL_FOR_REGISTRATION_PAGE, new UserFormRegistration());
    }

    @PostMapping
    public ModelAndView processRegistration(@Valid @ModelAttribute(NAME_USER_MODEL_FOR_REGISTRATION_PAGE)
                                                    UserFormRegistration userFormRegistration, BindingResult bindingResult, ModelAndView modelAndView) {

        isMatchPassword(userFormRegistration, bindingResult);

        try {
            Optional<User> user = isRegisteredEmail(userFormRegistration.getEmail(), bindingResult);
            if (bindingResult.hasErrors()) {
                LOGGER.info("There are errors in filling in the form " + REGISTRATION_PAGE);
                modelAndView.setViewName(REGISTRATION_PAGE);
            } else if (user.isPresent()) {
            } else {
                int userID = registrationUserLogic.registrationUser(new User(userFormRegistration));
                modelAndView.addObject("userID", userID);
                modelAndView.setViewName(REGISTRATION_SUCCESS_PAGE);
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | UnsupportedEncodingException e) {
            LOGGER.warn("While encrypting email any errors" + userFormRegistration.getEmail());
            modelAndView.setViewName(REGISTRATION_ERROR_PAGE);
        } catch (DataAccessException e) {
            modelAndView.setViewName(REGISTRATION_ERROR_PAGE);
        } catch (Exception e) {
            LOGGER.warn(e);
            modelAndView.setViewName(ERROR);
        }

//            try {
//                int userID = registrationUserLogic.registrationUser(new User(userFormRegistration));
//                modelAndView.addObject("userID", userID);
//                modelAndView.setViewName(REGISTRATION_SUCCESS_PAGE);
//                String stringEmailAES = "";
//                try {
//                    stringEmailAES = Aes.encrypt(userFormRegistration.getEmail(), ENCRYPT_KEY);
//                } catch (Exception e) {
//                    LOGGER.warn("While encrypting email any errors" + userFormRegistration.getEmail());
//                }
//                try {
//                    mailService.sendMail("vertex.academy.robot@gmail.com", userFormRegistration.getEmail(), "123",
//                            "http://localhost:8080/activationUser?activeUser=" + stringEmailAES);
//                } catch (Exception e) {
//                    LOGGER.warn("While mail sending error occurred " + userFormRegistration.getEmail());
//                }
//
//            } catch (DataAccessException ee) {
//                modelAndView.setViewName(REGISTRATION_ERROR_PAGE);
//            }
//        }
        modelAndView.addObject(NAME_USER_MODEL_FOR_REGISTRATION_PAGE, userFormRegistration);
        return modelAndView;
    }

    private void isMatchPassword(UserFormRegistration userFormRegistration, BindingResult bindingResult) {
        if (!registrationUserLogic.isMatchPassword(userFormRegistration)) {
            LOGGER.debug("when a user registration " + userFormRegistration.getEmail() + " were entered passwords do not match");
            bindingResult.rejectValue("verifyPassword", "error.verifyPassword", "Passwords do not match!");
        }
    }

    private void checkEmailAlreadyExists(String email, BindingResult bindingResult) {
        if (registrationUserLogic.checkEmailAlreadyExists(email)) {
            LOGGER.debug("That email |" + email + "| is already registered");
            bindingResult.rejectValue("email", "error.email", "User with that email is already registered!");
        }
    }

    private Optional<User> isRegisteredEmail(String email, BindingResult bindingResult) throws Exception {
        Optional<User> user = userLogic.isRegisteredEmail(email);
        if (user.isPresent() && user.get().isActive()) {
            LOGGER.debug("That email |" + email + "| is already registered");
            bindingResult.rejectValue("email", "error.email", "User with that email is already registered!");
        }
        return user;
    }
}