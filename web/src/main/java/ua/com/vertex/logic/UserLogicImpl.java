package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {
    private UserDaoInf userDao;

    @Autowired
    public UserLogicImpl(UserDaoInf userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<String> getAllUserIds() {
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
    public EnumMap<Role, Role> getListAllRoles() {
        return userDao.getListAllRoles();
    }

    @Override
    public int saveUserData(User user) {
        return userDao.saveUserData(user);
    }

    @Override
    public int activateUser(String email) {
        return userDao.activateUser(email);
    }
}
