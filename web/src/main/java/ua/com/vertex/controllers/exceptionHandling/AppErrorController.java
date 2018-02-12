package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@PropertySource("classpath:application.properties")
public class AppErrorController implements ErrorController {
    private static final Logger LOGGER = LogManager.getLogger(AppErrorController.class);
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String NOT_FOUND_LOG = "Resource not found";
    private static final String NOT_FOUND_MSG = "404 — unfortunately, the page you requested has not been found";
    private static final String UNKNOWN_ERROR = "Unknown error during logging in";
    public static final String LOGIN_ATTEMPTS = "Login attempts counter has been exceeded for this username.";

    @Value("${login.blocking.time.seconds}")
    private int blockingPeriod;

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model,
                              @RequestParam(required = false, name = "reason") String reason,
                              @RequestParam(required = false, name = "username") String username) {

        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            LOGGER.debug(NOT_FOUND_LOG);
            model.addAttribute(ERROR_MESSAGE, NOT_FOUND_MSG);

        } else if ("attempts".equals(reason)) {
            LOGGER.warn(getLogAttempts(username));
            model.addAttribute(ERROR_MESSAGE, getMsgAttempts());

        } else if ("unknown".equals(reason)) {
            model.addAttribute(ERROR_MESSAGE, UNKNOWN_ERROR);

        } else {
            Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
            LOGGER.warn(INTERNAL_SERVER_ERROR, throwable);
            model.addAttribute(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
        return ERROR;
    }

    private String getLogAttempts(String username) {
        return String.format("username=%s; %s", username, LOGIN_ATTEMPTS);
    }

    private String getMsgAttempts() {
        return String.format("%s The username has been blocked for %d minutes!", LOGIN_ATTEMPTS, blockingPeriod / 60);
    }

    @Override
    public String getErrorPath() {
        return ERROR;
    }
}
