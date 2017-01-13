package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    void deleteUser(int id);

    List<Integer> getAllUserIds();
}
