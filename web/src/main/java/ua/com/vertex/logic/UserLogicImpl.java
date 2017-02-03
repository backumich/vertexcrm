package ua.com.vertex.logic;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
    public List<User> getListUsers() throws SQLException {
        userDao.getListUsers();
        return userDao.getListUsers();
    }

    @Override
    public User getUserDetailsByID(int userId) throws SQLException {
        return userDao.getUserDetailsByID(userId);
    }

    @Override
    public String convertImage(byte[] image) {
        return Base64.encode(image);
    }

    @Override
    public HashMap<Integer, String> getListAllRoles() {
        return userDao.getListAllRoles();
    }

//    @Override
//    public Role getRoleById(int roleID) throws SQLException {
//        return userDao.getRoleById(roleID);
//    }
}
