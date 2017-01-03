package ua.com.vertex.logic.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

public interface RegistrationUserLogic {
    int registrationUser(User user) throws DataAccessException;

    boolean isMatchPassword(UserFormRegistration userFormRegistration);

    UserFormRegistration encryptPassword(UserFormRegistration userFormRegistration);

    User userFormRegistrationToUser(UserFormRegistration userFormRegistration);

    //todo: int? are you going to return anything else except zero and non-zero?
    int checkEmailAlreadyExists(UserFormRegistration userFormRegistration);
}
