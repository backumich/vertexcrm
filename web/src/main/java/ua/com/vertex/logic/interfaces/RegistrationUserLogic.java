package ua.com.vertex.logic.interfaces;

public interface RegistrationUserLogic {
    String registrationUser();

    void validationUserEmail();

    void validationUserPassword();

    void validationUserPasswordComplexity();
}
