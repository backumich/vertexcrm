package ua.com.vertex.controllers.exceptionHandling;

import org.springframework.dao.DataAccessException;

public class NoCertificateException extends RuntimeException {
    public NoCertificateException(String message, DataAccessException exception) {
        super(message, exception);
    }
}
