package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.User;

import java.util.List;

public interface UserLogic {

    List<String> getAllUserIds();

    User logIn(String email);
}
