package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.MailService;

import java.util.Optional;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationUserLogicImpl.class);
    public static final String OUR_EMAIL = "vertex.academy.robot@gmail.com";

    private final UserLogic userLogic;
    private final MailService mailService;
    private final EmailLogic emailLogic;

    @Override
    public boolean isEmailAlreadyExists(Optional<User> user) {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.checkEmailAlreadyExists(%s) ;", user));
        return user.isPresent() && user.get().isActive();
    }

    @Override
    @Transactional
    public boolean registerUser(UserFormRegistration userFormRegistration, BindingResult bindingResult) {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.registrationUser(%s) ;", userFormRegistration));

        boolean result = false;
        Optional<User> user = userLogic.userForRegistrationCheck(userFormRegistration.getEmail());
        if (isEmailAlreadyExists(user)) {
            LOGGER.warn("That email |" + userFormRegistration.getEmail() + "| is already registered");
            bindingResult.rejectValue("email", "error.email",
                    "User with that email is already registered!");
        } else {
            if (user.isPresent()) {
                userLogic.registrationUserUpdate(new User(userFormRegistration));
            } else {
                userLogic.registrationUserInsert(new User(userFormRegistration));
            }
            result = true;
            LOGGER.debug("Sending a message to the user - " + userFormRegistration.getEmail());
            mailService.sendMail(OUR_EMAIL, userFormRegistration.getEmail(), "Confirmation of registration",
                    emailLogic.createRegistrationMessage(userFormRegistration));
        }
        return result;
    }

    @Autowired
    public RegistrationUserLogicImpl(UserLogic userLogic, MailService mailService, EmailLogic emailLogic) {
        this.userLogic = userLogic;
        this.mailService = mailService;
        this.emailLogic = emailLogic;
    }
}

