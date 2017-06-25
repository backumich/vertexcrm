package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Optional;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationUserLogicImpl.class);

    private final UserLogic userLogic;

//    @Override
//    public boolean isVerifyPassword(UserFormRegistration userFormRegistration) {
//        LOGGER.debug(String.format("Call - RegistrationUserLogicImpl.checkPassword(%s) ;", userFormRegistration));
//        return userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword());
//    }

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
        }

        return result;
}

    @Autowired
    public RegistrationUserLogicImpl(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}

