package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.User;

import java.util.List;

public interface UserLogic {

    @SuppressWarnings("unused")
    List<String> getAllUserIds();

    List<User> searchUser(String userData);

}
