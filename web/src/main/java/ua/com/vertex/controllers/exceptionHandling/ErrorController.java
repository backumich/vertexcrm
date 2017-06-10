package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.utils.LogInfo;

@Controller
public class ErrorController {
    private static final String ERROR = "error";
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    private final LogInfo logInfo;

    @RequestMapping(value = "/error")
    public String handleError(Model model) {
        String message = "Logging in failed. Database might be temporarily unavailable";
        LOGGER.warn(logInfo.getId() + ": " + message);
        model.addAttribute("errorMessage", message);
        return ERROR;
    }

    @RequestMapping(value = "/404")
    public String handle404(Model model) {
        model.addAttribute("errorMessage", "404 — resource not found");
        return ERROR;
    }

    @RequestMapping(value = "/500")
    public String handle500(Model model) {
        String message = "Internal server error";
        LOGGER.warn(logInfo.getId() + ": " + message);
        model.addAttribute("errorMessage", message);
        return ERROR;
    }

    @Autowired
    public ErrorController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
