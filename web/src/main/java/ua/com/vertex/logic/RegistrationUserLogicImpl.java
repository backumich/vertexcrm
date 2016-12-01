package ua.com.vertex.logic;

import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

@Service
public class RegistrationUserLogicImpl implements RegistrationUserLogic {

    @Override
    public String registrationUser(User user) {

        return "123";
    }

    @Override
    public void validationUserEmail() {

    }

    @Override
    public void validationUserPassword() {

    }

    @Override
    public void validationUserPasswordComplexity() {

    }
}
