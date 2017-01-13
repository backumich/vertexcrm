package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.UserDaoImpl;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;
import java.util.stream.Collectors;

import static ua.com.vertex.beans.User.EMPTY_USER;

@Service
public class UserLogicImpl implements UserLogic {

    private final UserDaoImpl userDao;

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public User logIn(String email) {
        User user;
        if (email.isEmpty()) {
            user = EMPTY_USER;
        } else {
            user = userDao.logIn(email).orElse(EMPTY_USER);
        }

        return user;
    }

    @Autowired
    public UserLogicImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
}
