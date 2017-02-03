package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void saveImage(int userId, byte[] image, String imageType) throws Exception;

    Optional<byte[]> getImage(int userId, String imageType);
}
