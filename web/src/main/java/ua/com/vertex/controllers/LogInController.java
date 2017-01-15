package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.Storage;

@Controller
public class LogInController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String LOG_LOGIN_SUCCESS = "login successful";

    private static final String LOGIN = "logIn";
    private static final String LOGGED_IN = "loggedIn";
    private static final String ERROR = "error";

    @RequestMapping(value = "/logIn")
    public String showLogInPage() {
        String view = LOGIN;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!"anonymousUser".equals(principal)) {
                view = LOGGED_IN;
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/loggedIn")
    public String showLoggedIn() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!"anonymousUser".equals(principal) && storage.getEmail() == null) {
                storage.setEmail(((UserDetails) principal).getUsername());

                LOGGER.info(storage.getId() + LOG_LOGIN_SUCCESS);
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            return ERROR;
        }

        return LOGGED_IN;
    }

    @Autowired
    public LogInController(Storage storage) {
        this.storage = storage;
    }
}
