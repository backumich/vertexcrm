package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.controllers.UserController;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Optional;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserLogic userLogic;

    @Autowired
    public RegistrationUserLogicImpl(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @Override
    public boolean isVerifyPassword(UserFormRegistration userFormRegistration) {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.checkPassword(%s) ;", userFormRegistration));
        return userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Override
    public boolean isEmailAlreadyExists(Optional<User> user) {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.checkEmailAlreadyExists(%s) ;", user));
        return user.isPresent() && user.get().isActive();
    }

    @Override
    public boolean isRegisteredUser(UserFormRegistration userFormRegistration,
                                    BindingResult bindingResult) throws Exception {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.registrationUser(%s) ;", userFormRegistration));

        boolean result = false;
        if (!isVerifyPassword(userFormRegistration)) {
            LOGGER.debug("when a user registration " + userFormRegistration.getEmail() +
                    " were entered passwords do not match");
            bindingResult.rejectValue("verifyPassword", "error.verifyPassword",
                    "Passwords do not match!");
        } else {
            Optional<User> user = userLogic.userForRegistrationCheck(userFormRegistration.getEmail());
            if (isEmailAlreadyExists(user)) {
                LOGGER.warn("That email |" + userFormRegistration.getEmail() + "| is already registered");
                bindingResult.rejectValue("email", "error.email",
                        "User with that email is already registered!");
            } else {
                if (!bindingResult.hasErrors() && !user.isPresent()) {
                    userLogic.registrationUserInsert(new User(userFormRegistration));
                    result = true;
                } else if (!bindingResult.hasErrors() && user.isPresent()) {
                    userLogic.registrationUserUpdate(new User(userFormRegistration));
                    result = true;
                }
            }
        }
        return result;
    }
}

