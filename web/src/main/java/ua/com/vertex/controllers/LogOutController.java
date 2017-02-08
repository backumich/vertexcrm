package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.LogInfo;

@Controller
public class LogOutController {

    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(LogOutController.class);

    private static final String LOGOUT = "logOut";
    private static final String LOGGED_OUT = "loggedOut";
    private static final String ERROR = "error";
    private static final String INDEX = "index";
    private static final String ANONYMOUS_USER = "anonymousUser";

    @RequestMapping(value = "/logOut")
    public String showLogOutPage() {
        LOGGER.debug(logInfo.getId() + LOGOUT + " page accessed");

        String view = LOGOUT;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (ANONYMOUS_USER.equals(principal)) {
                view = LOGGED_OUT;
            }
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/loggedOut")
    public String processLogOut() {
        LOGGER.info(logInfo.getId() + "logging out successful");
        return LOGGED_OUT;
    }

    @RequestMapping(value = "/logOutRefuse")
    public String processLogOutRefuse() {
        LOGGER.debug(logInfo.getId() + "logging out refused");
        return INDEX;
    }

    @Autowired
    public LogOutController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
