package ua.com.vertex.dao;


import ua.com.vertex.beans.User;

public interface UserDaoInf {

    @SuppressWarnings("unused")
    User getUser(long id);

    @SuppressWarnings("unused")
    void deleteUser(long id);
}
