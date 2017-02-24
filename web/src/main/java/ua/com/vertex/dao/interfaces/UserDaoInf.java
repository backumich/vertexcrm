package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    @SuppressWarnings("unused")
    void deleteUser(int id);

    @SuppressWarnings("unused")
    List<Integer> getAllUserIds();

    int addUserForCreateCertificate(User user);

    List<User> searchUser(String userData) throws Exception;
}