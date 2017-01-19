package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    User getUser(int id);

    User logIn(String email);

    User imagesCheck(User user);

    Optional<User> getUserByEmail(String email);
}
