package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoggingLogicImpl implements LoggingLogic {

    private final LogInfo logInfo;
    private final UserLogic userLogic;
    private final UserDaoInf userDao;

    private static final Logger LOGGER = LogManager.getLogger(LoggingLogicImpl.class);

    private static final String AUTHORITIES_ERROR = "0 or more than 1 authority found";
    private static final String ADMIN = Role.ADMIN.name();
    private static final String USER = Role.USER.name();
    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "userProfile";
    private static final String ERROR = "error";

    @Override
    public Optional<User> logIn(String email) {
        Optional<User> toReturn;

        if (email.isEmpty()) {
            toReturn = Optional.empty();
        } else {
            toReturn = userDao.logIn(email);
        }

        return toReturn;
    }

    public String setUser(String email, Model model) throws Exception {
        String view = requiredView();
        Optional<User> userOptional = userLogic.getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (USER_PAGE.equals(view)) {
                model.addAttribute("user", user);
                LOGGER.info(logInfo.getId() + "login successful");
            } else if (ADMIN_PAGE.equals(view)) {
                model.addAttribute("user", user);
                LOGGER.info(logInfo.getId() + "login successful");
            }
        } else {
            view = ERROR;
        }

        return view;
    }

    private String requiredView() throws Exception {
        String view;

        List<String> authorities = new ArrayList<>();
        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .forEach(e -> authorities.add(e.toString()));

        if (authorities.size() == 1) {
            if (ADMIN.equals(authorities.get(0))) {
                view = ADMIN_PAGE;
            } else if (USER.equals(authorities.get(0))) {
                view = USER_PAGE;
            } else {
                throw new Exception(AUTHORITIES_ERROR);
            }
        } else {
            throw new Exception(AUTHORITIES_ERROR);
        }

        return view;
    }

    public LoggingLogicImpl(LogInfo logInfo, UserLogic userLogic, UserDaoInf userDao) {
        this.logInfo = logInfo;
        this.userLogic = userLogic;
        this.userDao = userDao;
    }
}
