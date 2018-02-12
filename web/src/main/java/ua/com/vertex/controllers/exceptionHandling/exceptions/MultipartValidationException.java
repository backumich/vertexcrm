package ua.com.vertex.controllers.exceptionHandling.exceptions;

public class MultipartValidationException extends RuntimeException {
    public MultipartValidationException(String message) {
        super(message);
    }
}
