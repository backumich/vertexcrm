package ua.com.vertex.logic.interfaces;


import ua.com.vertex.utils.Role;

import java.util.List;

public interface UserLogic {

    List<String> getAllUserIds();

    Role logIn(String email, String password);
}
