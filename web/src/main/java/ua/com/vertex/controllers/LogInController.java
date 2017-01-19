package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.Role;
import ua.com.vertex.utils.Storage;

import java.util.Collection;

@Controller
public class LogInController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String LOG_LOGIN_SUCCESS = "login successful";
    private static final String AUTHORITIES_ERROR = "0 or more than 1 authority found";

    private static final String ADMIN = Role.ADMIN.name();
    private static final String USER = Role.USER.name();
    private static final String LOGIN = "logIn";
    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "user";
    private static final String ERROR = "error";
    private static final String ANONYMOUS_USER = "anonymousUser";

    @RequestMapping(value = "/logIn")
    public String showLogInPage() {
        String view = LOGIN;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!ANONYMOUS_USER.equals(principal)) {
                view = redirectView(view);
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/loggedIn")
    public String showLoggedIn() {
        String view = ERROR;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            storage.setEmail(((UserDetails) principal).getUsername());
            view = redirectView(view);
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    private String redirectView(String view) throws Exception {

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
            LOGGER.info(storage.getId() + LOG_LOGIN_SUCCESS);
        } else {
            throw new Exception(AUTHORITIES_ERROR);
        }

        return view;
    }

    @Autowired
    public LogInController(Storage storage) {
        this.storage = storage;
    }
}
