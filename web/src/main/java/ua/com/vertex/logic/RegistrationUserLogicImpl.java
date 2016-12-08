package ua.com.vertex.logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.dao.impl.UserDaoRealization;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic {

    @Autowired
    private UserDaoRealization userDaoRealization;

    @Override
    public String registrationUser(User user) {
        userDaoRealization.registrationUser(user);
        return null;
    }

    @Override
    public boolean isMatchPassword(UserFormRegistration userFormRegistration) {
        if (userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public UserFormRegistration encryptPassword(UserFormRegistration userFormRegistration) {
        userFormRegistration.setPassword(DigestUtils.md5Hex(userFormRegistration.getPassword()));
        return userFormRegistration;
    }

    @Override
    public User userFormRegistrationToUser(UserFormRegistration userFormRegistration) {
        User user = new User();
        user.setEmail(userFormRegistration.getEmail());
        user.setPassword(userFormRegistration.getPassword());
        user.setFirstName(userFormRegistration.getFirstName());
        user.setLastName(userFormRegistration.getLastName());
        user.setPhone(userFormRegistration.getPhone());
        return user;
    }

    @Override
    public void checkEmailAlreadyExists(UserFormRegistration userFormRegistration) {
        userDaoRealization.isRegisteredEmail(userFormRegistration);
    }
}
