package ua.com.vertex.logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.dao.impl.UserDaoRealization;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic, Validator {

    @Autowired
    private UserDaoRealization userDaoRealization;

    @Override
    public String registrationUser(User user) {
        userDaoRealization.registrationUser(user);
        return null;
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

    @Override
    public UserFormRegistration encryptPassword(UserFormRegistration userFormRegistration) {

        userFormRegistration.newBuilder().setPassword(DigestUtils.md5Hex(userFormRegistration.getPassword())).build();
        //user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        //DigestUtils.md5Hex(user.getPassword());
        return userFormRegistration;
    }

    @Override
    public User userFormRegistrationToUser(UserFormRegistration userFormRegistration) {

        User user = new User
                .Builder()
                .setEmail(userFormRegistration.getEmail())
                .setPassword(userFormRegistration.getPassword())
                .setFirstName(userFormRegistration.getFirstName())
                .setLastName(userFormRegistration.getLastName())
                .setPhone(userFormRegistration.getPhone())
                .getInstance();
        return user;
    }


    @Override
    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
