package ua.com.vertex.dao;


import ua.com.vertex.beans.User;

import java.util.List;

public interface UserDao {

    @SuppressWarnings("unused")
    User getUser(int id);

    @SuppressWarnings("unused")
    void deleteUser(long id);

    List<Integer> getAllUserIds();
}
