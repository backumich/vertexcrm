package ua.com.vertex.logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.controllers.UserController;
import ua.com.vertex.dao.interfaces.UserDaoRealizationInf;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

@Component
public class RegistrationUserLogicImpl implements RegistrationUserLogic {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private UserDaoRealizationInf userDaoRealization;

    public RegistrationUserLogicImpl() {
    }

    @Autowired
    public RegistrationUserLogicImpl(UserDaoRealizationInf userDaoRealization) {
        this.userDaoRealization = userDaoRealization;
    }

    @Override
    public int registrationUser(User user) throws DataAccessException {
        return userDaoRealization.registrationUser(user);
    }

    @Override
    public boolean isMatchPassword(UserFormRegistration userFormRegistration) {
        LOGGER.info("Check for a match on the password");
        return userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword());
    }

    @Override
    public UserFormRegistration encryptPassword(UserFormRegistration userFormRegistration) {
        LOGGER.info("Password encryption");
        //todo: here is the link to think about: https://youtu.be/rCIsuMEFRro
        userFormRegistration.setPassword(DigestUtils.md5Hex(userFormRegistration.getPassword()));
        return userFormRegistration;
    }

    @Override
    public User userFormRegistrationToUser(UserFormRegistration userFormRegistration) {
        LOGGER.info("Conversion of the model UserFormRegistration to User");
        User user = new User();
        user.setEmail(userFormRegistration.getEmail());
        user.setPassword(userFormRegistration.getPassword());
        user.setFirstName(userFormRegistration.getFirstName());
        user.setLastName(userFormRegistration.getLastName());
        user.setPhone(userFormRegistration.getPhone());
        return user;
    }

    @Override
    public int checkEmailAlreadyExists(UserFormRegistration userFormRegistration) {
        return userDaoRealization.isRegisteredEmail(userFormRegistration.getEmail());
    }
}
