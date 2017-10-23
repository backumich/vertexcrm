package ua.com.vertex.logic.interfaces;

import org.springframework.ui.Model;
import ua.com.vertex.beans.User;

import java.util.Optional;

public interface LoggingLogic {

    Optional<User> logIn(String email) ;

    String setUser(String email, Model model);
}
