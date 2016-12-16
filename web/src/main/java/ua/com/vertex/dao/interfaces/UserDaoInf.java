package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;

public interface UserDaoInf {

    @SuppressWarnings("unused")
    User getUser(int id);

    @SuppressWarnings("unused")
    void deleteUser(int id);

    List<Integer> getAllUserIds();
}
