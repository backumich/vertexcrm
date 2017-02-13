package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    List<User> getListUsers() throws SQLException;

    Optional<User> getUserDetailsByID(int userId) throws SQLException;

    String convertImage(byte[] image);

    EnumMap<Role, Role> getListAllRoles();

    int saveUserData(User user);

}
