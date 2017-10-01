package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.com.vertex.context.SecurityWebConfig.*;

@Controller
public class AppErrorController implements ErrorController {
    private static final Logger LOGGER = LogManager.getLogger(AppErrorController.class);
    private static final String ERROR = "error";
    private static final String LOGIN = "logIn";
    private static final String CAPTCHA = "captcha";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String NOT_FOUND = "404 — unfortunately, the page you requested has not been found";
    private static final String USER_BLOCKED = " The username has been blocked for a period of time!";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String view = ERROR;

        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute(ERROR_MESSAGE, NOT_FOUND);

        } else if (UNKNOWN_ERROR.equals(throwable.getMessage())) {
            LOGGER.warn(UNKNOWN_ERROR, throwable);
            model.addAttribute(ERROR_MESSAGE, UNKNOWN_ERROR);

        } else if (LOGIN_ATTEMPTS.equals(throwable.getMessage())) {
            LOGGER.debug(LOGIN_ATTEMPTS);
            model.addAttribute(ERROR_MESSAGE, LOGIN_ATTEMPTS + USER_BLOCKED);

        } else if (RE_CAPTCHA.equals(throwable.getMessage())) {
            LOGGER.debug(RE_CAPTCHA);
            model.addAttribute(CAPTCHA, true);
            view = LOGIN;

        } else {
            LOGGER.warn(INTERNAL_SERVER_ERROR, throwable);
            model.addAttribute(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
        return view;
    }

    @Override
    public String getErrorPath() {
        return ERROR;
    }
}
