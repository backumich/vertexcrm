package ua.com.vertex.dao;


import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;

import java.util.List;

public interface UserDaoInf {

    @SuppressWarnings("unused")
    User getUser(long id);

    @SuppressWarnings("unused")
    void deleteUser(long id);

    List<Integer> getAllUserIds();

    void registrationUser(User user);

    void isRegisteredEmail(UserFormRegistration userFormRegistration);
}
