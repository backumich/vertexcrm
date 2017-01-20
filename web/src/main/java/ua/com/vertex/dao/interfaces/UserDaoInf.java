package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserMainData;

import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    void deleteUser(int id);

    List<Integer> getAllUserIds();

    List<UserMainData> getListUsers();

    User getUserDetailsByID(int UserID);

}
