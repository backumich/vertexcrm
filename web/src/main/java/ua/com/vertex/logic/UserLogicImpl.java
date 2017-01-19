package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserMainData;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    @Autowired
    private UserDaoInf userDao;

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public List<UserMainData> getListUsers() {
        userDao.getListUsers();
        return userDao.getListUsers();
    }

    @Override
    public User getUserDetails(int userID) {
        //userDao.getUserDetails(userID);
        return userDao.getUserDetails(userID);
    }


}
