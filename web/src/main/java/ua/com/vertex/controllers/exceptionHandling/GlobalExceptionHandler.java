package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.vertex.utils.LogInfo;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR = "error";
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    private final LogInfo logInfo;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        LOGGER.warn(logInfo.getId(), e);
        return ERROR;
    }

    public GlobalExceptionHandler(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}