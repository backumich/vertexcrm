package ua.com.vertex.logic;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Collection;
import java.util.Optional;

@Service
public class LoggingLogicImpl implements LoggingLogic {
    private static final String AUTHORITIES_ERROR = "0 or more than 1 authority found";
    private static final String ADMIN = Role.ROLE_ADMIN.name();
    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "userProfile";

    private final UserLogic userLogic;
    private final UserDaoInf userDao;

    @Override
    public Optional<User> logIn(String email)  {
        Optional<User> toReturn;

        if (email.isEmpty()) {
            toReturn = Optional.empty();
        } else {
            toReturn = userDao.logIn(email);
        }

        return toReturn;
    }

    public LoggingLogicImpl(UserLogic userLogic, UserDaoInf userDao) {
        this.userLogic = userLogic;
        this.userDao = userDao;
    }

    private String requiredView() throws Exception {
        String view = USER_PAGE;
        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.size() != 1) {
            throw new Exception(AUTHORITIES_ERROR);
        }
        if (ADMIN.equals(authorities.iterator().next().toString())) {
            view = ADMIN_PAGE;
        }

        return view;
    }

    @Override
    public String setUser(String email, Model model) throws Exception {
        String view = requiredView();
        User user = userLogic.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Not logged in: failed to fetch login details"));
        model.addAttribute("user", user);

        return view;
    }
}
