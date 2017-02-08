package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.logic.LoggingLogicImpl;
import ua.com.vertex.utils.LogInfo;

@Controller
public class LogInController {

    private final LogInfo logInfo;
    private final LoggingLogicImpl loggingLogic;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String LOGIN = "logIn";
    private static final String ERROR = "error";
    private static final String ANONYMOUS_USER = "anonymousUser";

    @RequestMapping(value = "/logIn")
    public String showLogInPage(Model model) {
        String view = LOGIN;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!ANONYMOUS_USER.equals(principal)) {
                view = loggingLogic.setUser(logInfo.getEmail(), model);
            }
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/loggedIn")
    public String showLoggedIn(Model model) {

        String view;
        try {
            view = loggingLogic.setUser(logInfo.getEmail(), model);
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    @Autowired
    public LogInController(LogInfo logInfo, LoggingLogicImpl loggingLogic) {
        this.logInfo = logInfo;
        this.loggingLogic = loggingLogic;
    }
}
