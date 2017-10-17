package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogOutController {
    private static final Logger logger = LogManager.getLogger(LogOutController.class);
    private static final String LOGOUT = "logOut";
    private static final String INDEX = "index";

    @RequestMapping(value = "/logOut")
    @PreAuthorize("isAuthenticated()")
    public String showLogOutPage() {
        logger.debug(LOGOUT + " page accessed");
        return LOGOUT;
    }

    @RequestMapping(value = "/logOutRefuse")
    @PreAuthorize("isAuthenticated()")
    public String processLogOutRefuse() {
        logger.debug("Logging out refused");
        return INDEX;
    }
}
