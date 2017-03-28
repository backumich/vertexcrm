package ua.com.vertex.logic.interfaces;

import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

import java.util.Optional;

public interface RegistrationUserLogic {

    void checkPassword(UserFormRegistration userFormRegistration, BindingResult bindingResult);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    void checkEmailAlreadyExists(String eMail, Optional<User> user, BindingResult bindingResult);

    boolean isRegisteredUser(UserFormRegistration userFormRegistration,
                             BindingResult bindingResult) throws Exception;
}
