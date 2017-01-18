package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.Role;
import ua.com.vertex.utils.Storage;

import java.util.List;

@Controller
public class LogInController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String LOG_LOGIN_SUCCESS = "login successful";

    private static final String ADMIN = Role.ADMIN.toString();
    private static final String USER = Role.USER.toString();
    private static final String LOGIN = "logIn";
    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "user";
    private static final String ERROR = "error";
    private static final String INDEX = "index";
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
        String view = INDEX;

        try {
            view = redirectView(view);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            storage.setEmail(((UserDetails) principal).getUsername());

            LOGGER.info(storage.getId() + LOG_LOGIN_SUCCESS);
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    private String redirectView(String view) {
        @SuppressWarnings("unchecked") List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.size() > 0) {
            if (ADMIN.equals(authorities.get(0).toString())) {
                view = ADMIN_PAGE;
            } else if (USER.equals(authorities.get(0).toString())) {
                view = USER_PAGE;
            }
        }

        return view;
    }

    @Autowired
    public LogInController(Storage storage) {
        this.storage = storage;
    }
}
