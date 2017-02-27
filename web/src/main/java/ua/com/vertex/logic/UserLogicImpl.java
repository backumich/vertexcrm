package ua.com.vertex.logic;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);
    private final UserDaoInf userDao;

    @Autowired
    public UserLogicImpl(UserDaoInf userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<String> getAllUserIds() {
        LOGGER.debug("Call - userDao.getAllUserIds() ;");
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public List<User> searchUser(String userData) throws Exception {
        LOGGER.debug(String.format("Call - userDao.searchUser(%s) ;", userData));
        return userDao.searchUser(userData);
    }
}
