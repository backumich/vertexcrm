package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;


public interface RegistrationUserLogic {
    String registrationUser(User user);

    void validationUserEmail();

    void validationUserPassword();

    void validationUserPasswordComplexity();

    UserFormRegistration encryptPassword(UserFormRegistration userFormRegistration);

    User userFormRegistrationToUser(UserFormRegistration userFormRegistration);
}
