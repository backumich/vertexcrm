package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.UserMainData;

import java.util.List;

public interface UserLogic {

    List<String> getAllUserIds();

    List<UserMainData> getListUsers();
}
