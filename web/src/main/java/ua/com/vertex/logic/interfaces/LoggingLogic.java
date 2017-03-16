package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.User;

import java.util.Optional;

public interface LoggingLogic {

    Optional<User> logIn(String email);
}
