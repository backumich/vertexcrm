package ua.com.vertex.controllers.exceptionHandling;

public class GlobalException extends RuntimeException {
    public GlobalException(RuntimeException exception, String message) {
        super(message, exception);
    }
}
