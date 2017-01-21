package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    Optional<User> getUserByEmail(String email);

    Optional<User> logIn(String username);

    void deleteUser(int id);

    List<Integer> getAllUserIds();

    Optional<User> saveImage(int userId, byte[] image, String imageType) throws Exception;
}
