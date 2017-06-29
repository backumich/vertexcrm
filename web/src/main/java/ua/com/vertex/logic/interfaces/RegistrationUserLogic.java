package ua.com.vertex.logic.interfaces;

import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

import java.util.Optional;

public interface RegistrationUserLogic {

//    boolean isVerifyPassword(UserFormRegistration userFormRegistration);

    boolean isEmailAlreadyExists(Optional<User> user);

    boolean isRegisteredUser(UserFormRegistration userFormRegistration,
                             BindingResult bindingResult) throws Exception;
}
