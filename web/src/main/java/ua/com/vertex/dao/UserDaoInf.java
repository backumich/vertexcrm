package ua.com.vertex.dao;


import ua.com.vertex.beans.User;

import java.util.List;

public interface UserDaoInf {

    @SuppressWarnings("unused")
    User getUser(long id);

    @SuppressWarnings("unused")
    void deleteUser(long id);

    List<Integer> getAllUserIds();

    List<User> getAllUsers();
}
