package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    Optional<User> logIn(String email);

    User imagesCheck(User user);

    void saveImage(int userId, byte[] image, String imageType) throws Exception;
}
