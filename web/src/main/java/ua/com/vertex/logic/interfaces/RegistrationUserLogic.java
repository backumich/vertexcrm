package ua.com.vertex.logic.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

public interface RegistrationUserLogic {
    int registrationUser(User user) throws DataAccessException;

    boolean isMatchPassword(UserFormRegistration userFormRegistration);

    String encryptPassword(String password);

    User userFormRegistrationToUser(UserFormRegistration userFormRegistration);

    Boolean checkEmailAlreadyExists(String email);
}
