package ua.com.vertex.logic.interfaces;


import ua.com.vertex.utils.UserRole;

import java.util.List;

public interface UserLogic {

    List<String> getAllUserIds();

    UserRole logIn(String email, String password);
}
