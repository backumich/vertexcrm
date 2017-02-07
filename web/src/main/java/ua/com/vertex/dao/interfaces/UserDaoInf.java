package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserDaoInf {

    Optional<User> getUser(int id);

    void deleteUser(int id);

    List<Integer> getAllUserIds();

    List<User> getListUsers() throws SQLException;

    User getUserDetailsByID(int userID) throws SQLException;

    HashMap<Role, Role> getListAllRoles();

//    Role getRoleById(int roleID) throws SQLException;

    int saveUserData(User user);

}
