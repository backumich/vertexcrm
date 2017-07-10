package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.utils.LogInfo;

@Controller
public class LogInController {
    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);
    private static final String LOGIN = "logIn";
    private static final String ERROR = "error";
    private final LogInfo logInfo;
    private final LoggingLogic loggingLogic;

    @RequestMapping(value = "/logIn")
    public String showLogInPage(Model model) {

        String view = LOGIN;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                view = loggingLogic.setUser(logInfo.getEmail(), model);
            }
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e);
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
            LOGGER.warn(logInfo.getId(), e);
            view = ERROR;
        }

        return view;
    }

    @Autowired
    public LogInController(LogInfo logInfo, LoggingLogic loggingLogic) {
        this.logInfo = logInfo;
        this.loggingLogic = loggingLogic;
    }
}
