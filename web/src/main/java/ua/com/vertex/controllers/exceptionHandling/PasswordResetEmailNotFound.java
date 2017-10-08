package ua.com.vertex.controllers.exceptionHandling;

public class PasswordResetEmailNotFound extends RuntimeException {
    public PasswordResetEmailNotFound(String message) {
        super(message);
    }
}
