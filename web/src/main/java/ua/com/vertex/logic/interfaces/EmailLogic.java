package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.UserFormRegistration;

public interface EmailLogic {
    String createRegistrationMessage(UserFormRegistration user);

    String createPasswordResetMessage(String email);

    boolean verifyEmail(String email);
}
