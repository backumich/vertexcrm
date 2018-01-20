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
    private static final String NOT_FOUND = "404 — unfortunately, the page you requested has not been found";
    public static final String UNKNOWN_ERROR = "Unknown error during logging in";
    public static final String LOGIN_ATTEMPTS = "Login attempts counter has been exceeded for this username!";
    public static final String ATTEMPTS = "attempts";
    public static final String UNKNOWN = "unknown";

    @Value("${login.blocking.time.seconds}")
    private int blockingPeriod;

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model,
                              @RequestParam(required = false, name = "reason") String reason) {

        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            LOGGER.debug(NOT_FOUND);
            model.addAttribute(ERROR_MESSAGE, NOT_FOUND);

        } else if (UNKNOWN.equals(reason)) {
            model.addAttribute(ERROR_MESSAGE, UNKNOWN_ERROR);

        } else if (ATTEMPTS.equals(reason)) {
            LOGGER.warn(LOGIN_ATTEMPTS);
            model.addAttribute(ERROR_MESSAGE, LOGIN_ATTEMPTS +
                    String.format(" The username has been blocked for %d minutes!", blockingPeriod / 60));

        } else {
            LOGGER.warn(INTERNAL_SERVER_ERROR, throwable);
            model.addAttribute(ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
        return ERROR;
    }

    @Override
    public String getErrorPath() {
        return ERROR;
    }
}
