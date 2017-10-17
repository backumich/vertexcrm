package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.SocketTimeoutException;

import static ua.com.vertex.controllers.PasswordResetEmailController.PASSWORD_RESET;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    public static final String EMAIL_NOT_FOUND = "emailNotFound";

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

    @ExceptionHandler({CannotGetJdbcConnectionException.class, SocketTimeoutException.class})
    public String handleCannotGetJdbcConnectionException(Exception e, Model model) {
        LOGGER.warn(e, e);
        model.addAttribute(ERROR_MESSAGE, "Database might temporarily be unavailable");
        return ERROR;
    }

    @ExceptionHandler(PasswordResetEmailNotFound.class)
    public String handlePasswordResetEmailNotFound(PasswordResetEmailNotFound e, Model model) {
        LOGGER.debug(e, e);
        model.addAttribute(EMAIL_NOT_FOUND, true);
        return PASSWORD_RESET;
    }

    @ExceptionHandler(UpdatedPasswordNotSaved.class)
    public String handleUpdatedPasswordNotSaved(UpdatedPasswordNotSaved e, Model model) {
        LOGGER.debug(e, e);
        model.addAttribute(ERROR_MESSAGE, "The new password was not saved. Please, try again");
        return ERROR;
    }
}