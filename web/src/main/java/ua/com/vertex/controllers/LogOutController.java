package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.LogInfo;

@Controller
public class LogOutController {

    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(LogOutController.class);

    private static final String LOGOUT = "logOut";
    private static final String INDEX = "index";

    @RequestMapping(value = "/logOut")
    @PreAuthorize("isAuthenticated()")
    public String showLogOutPage() {
        LOGGER.debug(logInfo.getId() + LOGOUT + " page accessed");
        return LOGOUT;
    }

    @RequestMapping(value = "/logOutRefuse")
    @PreAuthorize("isAuthenticated()")
    public String processLogOutRefuse() {
        LOGGER.debug(logInfo.getId() + "logging out refused");
        return INDEX;
    }

    @Autowired
    public LogOutController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
