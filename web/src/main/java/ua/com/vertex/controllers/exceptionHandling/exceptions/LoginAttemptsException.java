package ua.com.vertex.controllers.exceptionHandling.exceptions;

public class LoginAttemptsException extends RuntimeException {
    public LoginAttemptsException(String message) {
        super(message);
    }
}
