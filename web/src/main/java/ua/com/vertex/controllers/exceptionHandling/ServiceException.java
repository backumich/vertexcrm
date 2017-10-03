package ua.com.vertex.controllers.exceptionHandling;

public class ServiceException extends RuntimeException {
    public ServiceException(String message, RuntimeException exception) {
        super(message, exception);
    }
}
