package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface UserLogic {

    List<String> getAllUserIds();

    List<User> getListUsers() throws SQLException;

    User getUserDetailsByID(int userId) throws SQLException;

    //User getUserDetailsByID(int userID);
}
