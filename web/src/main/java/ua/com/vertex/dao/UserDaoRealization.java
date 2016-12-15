package ua.com.vertex.dao;


import ua.com.vertex.beans.User;

import java.util.List;

public interface UserDaoRealizationInf {

    @SuppressWarnings("unused")
    User getUser(long id);

    @SuppressWarnings("unused")
    void deleteUser(long id);

    List<Integer> getAllUserIds();

    void registrationUser(User user);

    int isRegisteredEmail(String email);
}
