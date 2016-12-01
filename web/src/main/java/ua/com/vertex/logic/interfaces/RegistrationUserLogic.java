package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.User;

public interface RegistrationUserLogic {
    String registrationUser(User user);

    void validationUserEmail();

    void validationUserPassword();

    void validationUserPasswordComplexity();
}
