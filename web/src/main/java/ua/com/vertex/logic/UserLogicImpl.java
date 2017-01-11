package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Role;
import ua.com.vertex.utils.Storage;

import java.util.List;
import java.util.stream.Collectors;

import static ua.com.vertex.beans.User.EMPTY_USER;
import static ua.com.vertex.utils.Role.*;

@Service
public class UserLogicImpl implements UserLogic {

    private final UserDaoInf userDao;
    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);
    private static final String LOG_IN_DATA_PROCESSED = "log in data processed";

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }

    @Override
    public Role logIn(String email, String password) {
        final int strength = 10;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);
        User receivedData = userDao.logIn(email).orElse(EMPTY_USER);

        Role userRole = NONE;
        if (!receivedData.equals(EMPTY_USER) && encoder.matches(password, receivedData.getPassword())) {
            userRole = receivedData.getRole() == ADMIN ? ADMIN : USER;
        }

        LOGGER.info(storage.getId() + LOG_IN_DATA_PROCESSED);

        return userRole;
    }

    @Autowired
    public UserLogicImpl(UserDaoInf userDao, Storage storage) {
        this.userDao = userDao;
        this.storage = storage;
    }
}
