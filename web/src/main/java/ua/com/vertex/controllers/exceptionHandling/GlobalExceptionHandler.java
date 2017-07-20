package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.vertex.utils.LogInfo;

import java.net.SocketTimeoutException;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";

    private final LogInfo logInfo;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        LOGGER.warn(logInfo.getId(), e);
        return ERROR;
    }

    @ExceptionHandler(NoCertificateException.class)
    public String handleNoCertificateException(Exception e, Model model) {
        LOGGER.warn(logInfo.getId(), e);
        model.addAttribute(ERROR_MESSAGE, "No certificate with this ID");
        return CERTIFICATE_DETAILS;
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, SocketTimeoutException.class})
    public String handleCannotGetJdbcConnectionException(Exception e, Model model) {
        LOGGER.warn(logInfo.getId(), e);
        model.addAttribute(ERROR_MESSAGE, "Database might temporarily be unavailable");
        return ERROR;
    }

    public GlobalExceptionHandler(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}