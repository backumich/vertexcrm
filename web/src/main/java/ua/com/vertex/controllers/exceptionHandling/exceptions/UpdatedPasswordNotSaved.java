package ua.com.vertex.controllers.exceptionHandling.exceptions;

public class UpdatedPasswordNotSaved extends RuntimeException {
    public UpdatedPasswordNotSaved(String message) {
        super(message);
    }
}
