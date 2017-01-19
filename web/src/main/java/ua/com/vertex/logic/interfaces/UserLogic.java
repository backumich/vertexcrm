package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    Optional<User> getUser(int id);

    Optional<User> logIn(String email);

    User imagesCheck(User user);

    Optional<User> getUserByEmail(String email);
}
