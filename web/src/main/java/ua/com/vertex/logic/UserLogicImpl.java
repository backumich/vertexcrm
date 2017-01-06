package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.UserLogIn;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.UserRole;

import java.util.List;
import java.util.stream.Collectors;

import static ua.com.vertex.beans.UserLogIn.EMPTY_USER_LOG_IN;
import static ua.com.vertex.utils.UserRole.*;

@Service
public class UserLogicImpl implements UserLogic {

    private final UserDaoInf userDao;

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public UserRole logIn(String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        UserLogIn receivedData = userDao.logIn(email).orElse(EMPTY_USER_LOG_IN);

        UserRole userRole = NONE;
        if (!receivedData.equals(EMPTY_USER_LOG_IN) && encoder.matches(password, receivedData.getPassword())) {
            userRole = receivedData.getUserRole() == ADMIN ? ADMIN : USER;
        }

        return userRole;
    }

    @Autowired
    public UserLogicImpl(UserDaoInf userDao) {
        this.userDao = userDao;
    }
}
