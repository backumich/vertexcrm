package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.vertex.controllers.exceptionHandling.exceptions.MultipartValidationException;
import ua.com.vertex.controllers.exceptionHandling.exceptions.NoCertificateException;
import ua.com.vertex.controllers.exceptionHandling.exceptions.UpdatedPasswordNotSaved;
import ua.com.vertex.utils.EmailExtractor;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    public static final String ERROR_MESSAGE = "errorMessage";
    private static final String IMAGE_ERROR = "imageError";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String LOGIN = "logIn";
    private static final String ACCESS_DENIED = "403";

    private final EmailExtractor emailExtractor;

    @Autowired
    public GlobalExceptionHandler(EmailExtractor emailExtractor) {
        this.emailExtractor = emailExtractor;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        LOGGER.warn(e, e);
        return ERROR;
    }

    @ExceptionHandler(NoCertificateException.class)
    public String handleNoCertificateException(Exception e, Model model) {
        LOGGER.warn(e);
        model.addAttribute(ERROR_MESSAGE, "No certificate with this ID");
        return CERTIFICATE_DETAILS;
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, SocketTimeoutException.class, DataAccessException.class})
    public String handleConnectionException(Exception e, Model model) {
        LOGGER.warn(e, e);
        model.addAttribute(ERROR_MESSAGE, "Oops, something went wrong. Try in a minute please.");
        return ERROR;
    }

    @ExceptionHandler(MultipartValidationException.class)
    public String handleMultipartValidationException(MultipartValidationException e, Model model) {
        LOGGER.debug(e);
        model.addAttribute(ERROR_MESSAGE, e.getMessage());
        return IMAGE_ERROR;
    }

    @ExceptionHandler(UpdatedPasswordNotSaved.class)
    public String handleUpdatedPasswordNotSaved(UpdatedPasswordNotSaved e, Model model) {
        LOGGER.debug(e);
        model.addAttribute(ERROR_MESSAGE, "The new password was not saved. Please, try again");
        return ERROR;
    }

    @ExceptionHandler({AccessDeniedException.class, PreAuthenticatedCredentialsNotFoundException.class})
    public String handleAccessOrAuthenticationException(AccessDeniedException e) {
        String view;
        if (emailExtractor.getEmailFromAuthentication() == null) {
            view = LOGIN;
        } else {
            view = ACCESS_DENIED;
            LOGGER.warn(e, e);
        }
        return view;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpServletRequest request) {
        String view;
        if (emailExtractor.getEmailFromAuthentication() == null) {
            view = LOGIN;
        } else {
            view = request.getServletPath();
        }
        return view;
    }
}