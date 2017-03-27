package ua.com.vertex.logic;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    private static final int PASSWORD_STRENGTH = 10;

    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);
    private final UserDaoInf userDao;

    @Override
    public List<String> getAllUserIds() {
        LOGGER.debug("Call - userDao.getAllUserIds() ;");
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUser(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void saveImage(int userId, byte[] image, String imageType) throws Exception {
        userDao.saveImage(userId, image, imageType);
    }

    @Override
    public Optional<byte[]> getImage(int userId, String imageType) {
        return userDao.getImage(userId, imageType);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    @Override
    public Optional<User> getUserDetailsByID(int userId) throws SQLException {
        return userDao.getUserDetailsByID(userId);
    }

    @Override
    public EnumMap<Role, Role> getAllRoles() {
        return userDao.getAllRoles();
    }

    @Override
    public int saveUserData(User user) {
        return userDao.saveUserData(user);
    }

    @Override
    public int activateUser(String email) {
        return userDao.activateUser(email);
    }

    @Override
    public List<User> searchUser(String userData) throws Exception {
        LOGGER.debug(String.format("Call - userDao.searchUser(%s) ;", userData));
        return userDao.searchUser(userData);
    }

    @Override
    public Optional<User> isRegisteredUser(String userEmail) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.isRegisteredEmail(%s) ;", userEmail));
        return userDao.isRegisteredUser(userEmail);
    }

    @Override
    public int registrationUserInsert(User user) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.registrationUserInsert(%s) ;", user));
        user.setPassword(encryptPassword(user.getPassword()));
        return userDao.registrationUserInsert(user);
    }

    @Override
    public int registrationUserUpdate(User user) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.registrationUserUpdate(%s) ;", user));
        user.setPassword(encryptPassword(user.getPassword()));
        return userDao.registrationUserUpdate(user);
    }

    @Override
    public String encryptPassword(String password) {
        LOGGER.debug("Password encryption");
        return new BCryptPasswordEncoder(PASSWORD_STRENGTH).encode(password);
    }

    @Override
    public boolean isMatchPassword(UserFormRegistration userFormRegistration) {
        LOGGER.debug("Check for a match on the password");
        return userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword());
    }

    @Override
    public User userFormRegistrationToUser(UserFormRegistration userFormRegistration) {
        LOGGER.debug("Conversion of the model UserFormRegistration to User");
        User user = new User();
        user.setEmail(userFormRegistration.getEmail());
        user.setPassword(userFormRegistration.getPassword());
        user.setFirstName(userFormRegistration.getFirstName());
        user.setLastName(userFormRegistration.getLastName());
        user.setPhone(userFormRegistration.getPhone());
        return user;
    }

    @Autowired
    public UserLogicImpl(UserDaoInf userDao) {
        this.userDao = userDao;
    }

}
