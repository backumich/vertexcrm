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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);
    private final UserDaoInf userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public DataNavigator updateDataNavigator(DataNavigator dataNavigator) {
        LOGGER.debug("Update dataNavigator");
        if (dataNavigator.getCurrentNumberPage() == dataNavigator.getNextPage()) {
            dataNavigator.setCurrentNumberPage(1);
            dataNavigator.setNextPage(1);
        } else {
            dataNavigator.setCurrentNumberPage(dataNavigator.getNextPage());
        }
        try {
            int dataSize = userDao.getQuantityUsers();
            dataNavigator.setDataSize(dataSize);
            dataNavigator.setTotalPages(dataSize / dataNavigator.getRowPerPage());
            if (dataSize / dataNavigator.getRowPerPage() >= 0) {
                dataNavigator.setTotalPages(dataNavigator.getQuantityPages() + 1);
            }
            dataNavigator.setLastPage(dataNavigator.getQuantityPages());
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return dataNavigator;
    }


    @Override
    public List<User> getUsersPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get part data users list (dataNavigator)");
        List<User> users = null;
        try {
            users = userDao.getAllUsers(dataNavigator);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return users;
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
    public Optional<User> userForRegistrationCheck(String userEmail) throws DataAccessException {
        LOGGER.debug(String.format("Call - userDao.userForRegistrationCheck(%s) ;", userEmail));
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
