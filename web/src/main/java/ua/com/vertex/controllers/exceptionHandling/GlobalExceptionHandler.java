package ua.com.vertex.controllers.exceptionHandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.com.vertex.controllers.exceptionHandling.exceptions.MultipartValidationException;
import ua.com.vertex.controllers.exceptionHandling.exceptions.NoCertificateException;
import ua.com.vertex.utils.UtilFunctions;

import java.net.SocketTimeoutException;

import static ua.com.vertex.logic.UserLogicImpl.FILE_SIZE_EXCEEDED;
import static ua.com.vertex.logic.UserLogicImpl.FILE_TYPE_INVALID;

@ControllerAdvice
@PropertySource("classpath:application.properties")
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR = "error";
    private static final String IMAGE_ERROR = "imageError";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    public static final String ERROR_MESSAGE = "errorMessage";

    @Value("${image.size.bytes}")
    private int fileSizeInBytes;

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

    @ExceptionHandler(MultipartValidationException.class)
    public String handleMultipartValidationException(MultipartValidationException e, Model model) {
        if (FILE_SIZE_EXCEEDED.equals(e.getMessage())) {
            LOGGER.debug(e, e);
            model.addAttribute(ERROR_MESSAGE, "File size exceeded, max allowed is "
                    + UtilFunctions.humanReadableByteCount(fileSizeInBytes));
        } else if (FILE_TYPE_INVALID.equals(e.getMessage())) {
            LOGGER.debug(e, e);
            model.addAttribute(ERROR_MESSAGE, "You have chosen a file of invalid type!");
        }
        return IMAGE_ERROR;
    }
}