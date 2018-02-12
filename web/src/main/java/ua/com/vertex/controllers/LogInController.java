package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.utils.EmailExtractor;

@Controller
public class LogInController {
    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);
    private static final String LOGIN = "logIn";
    private final LoggingLogic loggingLogic;
    private final EmailExtractor emailExtractor;

    @GetMapping(value = "/logIn")
    public String showLogInPage(Model model) {
        LOGGER.debug(LOGIN + " page accessed");
        String email = emailExtractor.getEmailFromAuthentication();

        return email == null ? LOGIN : loggingLogic.setUser(email, model);
    }

    @GetMapping(value = "/loggedIn")
    public String showLoggedIn(Model model) {
        return loggingLogic.setUser(emailExtractor.getEmailFromAuthentication(), model);
    }

    @Autowired
    public LogInController(LoggingLogic loggingLogic, EmailExtractor emailExtractor) {
        this.loggingLogic = loggingLogic;
        this.emailExtractor = emailExtractor;
    }
}
