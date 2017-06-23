package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static ua.com.vertex.context.SecurityConfig.UNKNOWN_ERROR;

@Controller
public class ErrorController {
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final Logger LOGGER = LogManager.getLogger(ErrorController.class);

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String message;

        if (UNKNOWN_ERROR.equals(throwable.getMessage())) {
            message = UNKNOWN_ERROR;
            LOGGER.warn(message, throwable);
            model.addAttribute(ERROR_MESSAGE, message);
        } else {
            message = "Internal server error";
            LOGGER.warn(message, throwable);
            model.addAttribute(ERROR_MESSAGE, message);
        }
        return ERROR;
    }

    @RequestMapping(value = "/404")
    public String handle404(Model model) {
        model.addAttribute(ERROR_MESSAGE, "404 — resource not found");
        return ERROR;
    }
}
