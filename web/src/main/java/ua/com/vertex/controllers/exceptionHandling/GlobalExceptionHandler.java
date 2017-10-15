package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.SocketTimeoutException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        LOGGER.warn(e, e);
        return ERROR;
    }

    @ExceptionHandler(NoCertificateException.class)
    public String handleNoCertificateException(Exception e, Model model) {
        LOGGER.warn(e, e);
        model.addAttribute(ERROR_MESSAGE, "No certificate with this ID");
        return CERTIFICATE_DETAILS;
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, SocketTimeoutException.class, DataAccessException.class})
    public String handleConnectionException(Exception e, Model model) {
        LOGGER.warn(e, e);
        model.addAttribute(ERROR_MESSAGE, "Oops, something went wrong. Try in a minute please.");
        return ERROR;
    }
}