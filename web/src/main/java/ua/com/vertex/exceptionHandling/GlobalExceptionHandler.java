package ua.com.vertex.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.vertex.utils.LogInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR = "error";
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    private final LogInfo logInfo;

    @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception e) {
        LOGGER.warn(logInfo.getId(), e);
        return ERROR;
    }

    public GlobalExceptionHandler(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}