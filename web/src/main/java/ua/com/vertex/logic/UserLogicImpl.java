package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);
    private final UserDaoInf userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<String> getAllUserIds() throws DataAccessException {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserById(int id) throws DataAccessException {
        return userDao.getUser(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws DataAccessException {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void saveImage(int userId, byte[] image, String imageType) throws DataAccessException {
        userDao.saveImage(userId, image, imageType);
    }

    @Override
    public Optional<byte[]> getImage(int userId, String imageType) throws DataAccessException {
        return userDao.getImage(userId, imageType);
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        return userDao.getAllUsers();
    }

    public int getQuantityUsers() throws SQLException {
        return userDao.getQuantityUsers();
    }

    @Override
    public List<User> getUsersPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get part data users list (dataNavigator)");

        return userDao.getUsersPerPages(dataNavigator);
    }

    @Override
    public EnumMap<Role, Role> getAllRoles() throws DataAccessException {
        return userDao.getAllRoles();
    }

    @Override
    public int saveUserData(User user) throws DataAccessException {
        return userDao.saveUserData(user);
    }

    @Override
    public int activateUser(String email) throws DataAccessException {
        return userDao.activateUser(email);
    }

    @Override
    public List<User> searchUser(String userData) throws DataAccessException {
        return userDao.searchUser(userData);
    }

    @Override
    public Optional<User> userForRegistrationCheck(String userEmail) throws DataAccessException {
        return userDao.userForRegistrationCheck(userEmail);
    }

    @Override
    public void registrationUserInsert(User user) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.registrationUserInsert(%s) ;", user));
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.registrationUserInsert(user);
    }

    @Override
    public void registrationUserUpdate(User user) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.registrationUserUpdate(%s) ;", user));
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.registrationUserUpdate(user);
    }

    @Override
    public Map<Integer, String> getTeachers() throws DataAccessException {
        LOGGER.debug("Call - userDao.getTeachers()");

        return userDao.getTeachers().stream().collect(Collectors.toMap(User::getUserId,
                x -> x.getFirstName() + " " + x.getLastName() + " \'" + x.getEmail() + "\'"));
    }

    @Override
    public String encryptPassword(String password) {
        LOGGER.debug("Password encryption");
        return bCryptPasswordEncoder.encode(password);
    }

    @Autowired
    public UserLogicImpl(UserDaoInf userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getCourseUsers(int courseId) {

        LOGGER.debug(String.format("Call - accountingDaoInf.getCourseUsers(%s)", courseId));
        return userDao.getCourseUsers(courseId);
    }
}
