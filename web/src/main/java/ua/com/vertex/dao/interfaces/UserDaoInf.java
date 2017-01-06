package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserLogIn;

import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    Optional<UserLogIn> logIn(String username);

    void deleteUser(int id);

    List<Integer> getAllUserIds();
}
