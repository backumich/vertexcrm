package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

public interface RegistrationUserLogic {
    int registrationUser(User user);

    boolean isMatchPassword(UserFormRegistration userFormRegistration);

    UserFormRegistration encryptPassword(UserFormRegistration userFormRegistration);

    User userFormRegistrationToUser(UserFormRegistration userFormRegistration);

    int checkEmailAlreadyExists(UserFormRegistration userFormRegistration);
}
