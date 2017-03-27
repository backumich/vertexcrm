package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public void checkPassword(UserFormRegistration userFormRegistration, BindingResult bindingResult) {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.checkPassword(%s) ;", userFormRegistration));
        if (!userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword())) {
            LOGGER.debug("when a user registration " + userFormRegistration.getEmail() + " were entered passwords do not match");
            bindingResult.rejectValue("verifyPassword", "error.verifyPassword", "Passwords do not match!");
        }
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Override
    public void checkEmailAlreadyExists(String eMail, Optional<User> user, BindingResult bindingResult) {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.checkEmailAlreadyExists(%s) ;", user));
        if (user.isPresent() && user.get().isActive()) {
            LOGGER.warn("That email |" + eMail + "| is already registered");
            bindingResult.rejectValue("email", "error.email", "User with that email is already registered!");
        }
    }

    @Override
    public void registerationUser(UserFormRegistration userFormRegistration,
                                  BindingResult bindingResult) throws DataAccessException {
        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.registerationUser(%s) ;", userFormRegistration));
        checkPassword(userFormRegistration, bindingResult);

        if (!bindingResult.hasErrors()) {
            Optional<User> user = userLogic.userForRegistrationCheck(userFormRegistration.getEmail());
            checkEmailAlreadyExists(userFormRegistration.getEmail(), user, bindingResult);
            if (!bindingResult.hasErrors() && !user.isPresent()) {
                userLogic.registrationUserInsert(new User(userFormRegistration));
            } else if (!bindingResult.hasErrors() && user.isPresent()) {
                userLogic.registrationUserUpdate(new User(userFormRegistration));
            }
        }
    }


}

