package ua.com.vertex.logic;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
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
    public List<User> getUsersPerPages(DataNavigator dataNavigator) {
        List<User> users = null;
        if (dataNavigator.getCurrentNumberPage() == dataNavigator.getNextPage()) {
            dataNavigator.setCurrentNumberPage(1);
            dataNavigator.setNextPage(1);
        } else {
            dataNavigator.setCurrentNumberPage(dataNavigator.getNextPage());
        }

        try {
            users = userDao.getAllUsers(dataNavigator);
            int dataSize = userDao.getQuantityUsers();

            dataNavigator.setDataSize(dataSize);
            dataNavigator.setQuantityPages(dataSize / dataNavigator.getCurrentRowPerPage());
            if (dataSize / dataNavigator.getCurrentRowPerPage() >= 0) {
                dataNavigator.setQuantityPages(dataNavigator.getQuantityPages() + 1);
            }
            dataNavigator.setLastPage(dataNavigator.getQuantityPages());
        } catch (SQLException e) {
            LOGGER.warn(e);
        }
        return users;
    }

    @Override
    public List<Course> getCoursesPerPages(DataNavigator dataNavigator) {
        List<Course> courses = null;
        if (dataNavigator.getCurrentNumberPage() == dataNavigator.getNextPage()) {
            dataNavigator.setCurrentNumberPage(1);
            dataNavigator.setNextPage(1);
        } else {
            dataNavigator.setCurrentNumberPage(dataNavigator.getNextPage());
        }

        try {
            courses = userDao.getAllCourses(dataNavigator);
            int dataSize = userDao.getQuantityCourses();

            dataNavigator.setDataSize(dataSize);
            dataNavigator.setQuantityPages(dataSize / dataNavigator.getCurrentRowPerPage());
            if (dataSize / dataNavigator.getCurrentRowPerPage() >= 0) {
                dataNavigator.setQuantityPages(dataNavigator.getQuantityPages() + 1);
            }
            dataNavigator.setLastPage(dataNavigator.getQuantityPages());
        } catch (SQLException e) {
            LOGGER.warn(e);
        }
        return courses;
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

    @Autowired
    public UserLogicImpl(UserDaoInf userDao) {
        this.userDao = userDao;
    }

}
