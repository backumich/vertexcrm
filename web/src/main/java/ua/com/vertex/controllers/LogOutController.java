package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogOutController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogOutController.class);

    private static final String LOG_ENTRY = " page entered";
    private static final String LOG_OUT_SUCCESS = "Logging out successful";
    private static final String LOG_LOGOUT_REFUSE = "logging out refused";
    private static final String LOG_SESSION_START = "Session start";

    private static final String LOGOUT = "logOut";
    private static final String LOGGED_OUT = "loggedOut";
    private static final String ERROR = "error";
    private static final String INDEX = "index";
    private static final String ANONYMOUS_USER = "anonymousUser";

    @RequestMapping(value = "/logOut")
    public String showLogOutPage() {
        LOGGER.debug(storage.getId() + LOGOUT + LOG_ENTRY);

        String view = LOGOUT;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (ANONYMOUS_USER.equals(principal)) {
                view = LOGGED_OUT;
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/loggedOut")
    public String processLogOut(HttpServletRequest request) {
        LOGGER.info(storage.getId() + LOG_OUT_SUCCESS);

        String view = LOGGED_OUT;
        try {
            if (storage.getSessionId() == null && storage.getCount() > 2) {
                storage.setSessionId(request.getSession().getId());

                LOGGER.info(storage.getId() + LOG_SESSION_START);
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/logOutRefuse")
    public String processLogOutRefuse() {
        LOGGER.debug(storage.getId() + LOG_LOGOUT_REFUSE);
        return INDEX;
    }

    @Autowired
    public LogOutController(Storage storage) {
        this.storage = storage;
    }
}
