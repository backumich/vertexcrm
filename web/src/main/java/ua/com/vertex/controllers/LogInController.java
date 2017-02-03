package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.Collection;

import static ua.com.vertex.beans.User.EMPTY_USER;

@Controller
public class LogInController {

    private final LogInfo logInfo;
    private final UserLogic userLogic;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String AUTHORITIES_ERROR = "0 or more than 1 authority found";
    private static final String ADMIN = Role.ADMIN.name();
    private static final String USER = Role.USER.name();
    private static final String LOGIN = "logIn";
    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "userProfile";
    private static final String ERROR = "error";
    private static final String ANONYMOUS_USER = "anonymousUser";

    @RequestMapping(value = "/logIn")
    public String showLogInPage(Model model) {
        String view = LOGIN;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!ANONYMOUS_USER.equals(principal)) {
                view = setUser(logInfo.getEmail(), model);
            }
        } catch (Exception e) {
            LOGGER.debug(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/loggedIn")
    public String showLoggedIn(Model model) {

        String view;
        try {
            view = setUser(logInfo.getEmail(), model);
        } catch (Exception e) {
            LOGGER.debug(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    private String setUser(String email, Model model) throws Exception {
        String view = redirectView();
        User user = userLogic.getUserByEmail(email).orElse(EMPTY_USER);

        if (USER_PAGE.equals(view)) {
            model.addAttribute("user", user);
            LOGGER.info(logInfo.getId() + "login successful");
        } else if (ADMIN_PAGE.equals(view)) {
            model.addAttribute("user", user);
            LOGGER.info(logInfo.getId() + "login successful");
        }

        return view;
    }

    private String redirectView() throws Exception {
        String view = "";

        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.size() == 1) {
            for (Object authority : authorities) {
                if (ADMIN.equals(authority.toString())) {
                    view = ADMIN_PAGE;
                } else if (USER.equals(authority.toString())) {
                    view = USER_PAGE;
                } else {
                    throw new Exception(AUTHORITIES_ERROR);
                }
            }

        } else {
            throw new Exception(AUTHORITIES_ERROR);
        }

        return view;
    }

    @Autowired
    public LogInController(LogInfo logInfo, UserLogic userLogic) {
        this.logInfo = logInfo;
        this.userLogic = userLogic;
    }
}
