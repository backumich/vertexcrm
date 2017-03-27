package ua.com.vertex.logic.interfaces;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

import java.util.Optional;

public interface RegistrationUserLogic {

    void checkPassword(UserFormRegistration userFormRegistration, BindingResult bindingResult);

    void checkEmailAlreadyExists(String eMail, Optional<User> user,BindingResult bindingResult);

    void registerationUser( UserFormRegistration userFormRegistration,
                          BindingResult bindingResult) throws DataAccessException;
}
