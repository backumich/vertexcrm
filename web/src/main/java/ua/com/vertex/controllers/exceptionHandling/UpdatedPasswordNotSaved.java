package ua.com.vertex.controllers.exceptionHandling;

public class UpdatedPasswordNotSaved extends RuntimeException {
    public UpdatedPasswordNotSaved(String message) {
        super(message);
    }
}
