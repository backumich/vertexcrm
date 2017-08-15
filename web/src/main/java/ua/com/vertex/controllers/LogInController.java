package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.utils.EmailExtractor;

@Controller
public class LogInController {
    private static final String LOGIN = "logIn";
    private final LoggingLogic loggingLogic;
    private final EmailExtractor emailExtractor;

    @RequestMapping(value = "/logIn")
    public String showLogInPage(Model model) throws Exception {

        String view = LOGIN;
        String email = emailExtractor.getEmailFromAuthentication();
        if (email != null) {
            view = loggingLogic.setUser(email, model);
        }
        return view;
    }

    @RequestMapping(value = "/loggedIn")
    public String showLoggedIn(Model model) throws Exception {
        return loggingLogic.setUser(emailExtractor.getEmailFromAuthentication(), model);
    }

    @Autowired
    public LogInController(LoggingLogic loggingLogic, EmailExtractor emailExtractor) {
        this.loggingLogic = loggingLogic;
        this.emailExtractor = emailExtractor;
    }
}
