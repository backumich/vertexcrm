package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Role;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static ua.com.vertex.utils.Role.ADMIN;
import static ua.com.vertex.utils.Role.USER;

@Controller
@RequestMapping(value = "/logIn")
public class LogInController {

    private final UserLogic logic;
    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);

    private static final String ALREADY_LOGGED_IN = "loggedIn";
    private static final String LOG_LOGIN_SUCCESS = "login successful";
    private static final String LOG_EMPTY = "empty email or password";
    private static final String LOG_REQUEST = "log in request sent";

    private static final String LOG_IN = "login";
    private static final String LOG_IN_SUCCESS = "loginSuccess";
    private static final String ERROR = "error";

    private static final String USER_EMAIL = "userEmail";
    private static final String USER_ROLE = "userRole";
    private static final String USER_NOT_FOUND = "userNotFound";

    @GetMapping
    public String showLogInPage(HttpServletRequest request) {
        String view = LOG_IN;
        try {
            if (request.getSession().getAttribute(USER_ROLE) != null) {
                view = ALREADY_LOGGED_IN;
            }
        } catch (Throwable t) {
            view = ERROR;
        }

        return view;
    }

    @PostMapping
    public String processLogInRequest(@RequestParam String email, @RequestParam String password,
                                      Model model, HttpServletRequest request) {
        String view = LOG_IN_SUCCESS;

        LOGGER.info(storage.getId() + LOG_REQUEST);

        try {
            if (email.isEmpty() || password.isEmpty()) {
                model.addAttribute(USER_NOT_FOUND, true);
                view = LOG_IN;
                LOGGER.info(storage.getId() + LOG_EMPTY);
            } else {
                view = processLogInResponse(email, password, model, view, request);
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    private String processLogInResponse(String email, String password, Model model, String view,
                                        HttpServletRequest request) {
        switch (logic.logIn(email, password)) {
            case NONE:
                model.addAttribute(USER_NOT_FOUND, true);
                view = LOG_IN;
                break;
            case USER:
                setParameters(request, email, USER);
                break;
            case ADMIN:
                setParameters(request, email, ADMIN);
        }
        return view;
    }

    private void setParameters(HttpServletRequest request, String email, Role userRole) {
        HttpSession session = request.getSession();
        session.setAttribute(USER_ROLE, userRole);
        session.setAttribute(USER_EMAIL, email);
        storage.setEmail(email);
        LOGGER.info(storage.getId() + LOG_LOGIN_SUCCESS);
    }

    @Autowired
    public LogInController(UserLogic logic, Storage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
