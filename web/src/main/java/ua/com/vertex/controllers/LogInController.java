package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogInController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String LOG_LOGIN_SUCCESS = "login successful";
    private static final String LOG_ENTRY = " page entered";

    private static final String LOGIN = "login";
    private static final String LOGGED_IN = "loggedIn";
    private static final String ERROR = "error";

    @RequestMapping(value = "/logIn")
    public String showLogInPage(HttpServletRequest request) {
        LOGGER.debug(storage.getId() + LOGIN + LOG_ENTRY);

        String view = LOGIN;
        try {
            if (request.getUserPrincipal() != null) {
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
        LOGGER.info(storage.getId() + LOG_LOGIN_SUCCESS);
        return LOGGED_IN;
    }

    @Autowired
    public LogInController(Storage storage) {
        this.storage = storage;
    }
}
