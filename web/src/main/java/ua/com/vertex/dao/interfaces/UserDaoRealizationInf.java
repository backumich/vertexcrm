package ua.com.vertex.dao.interfaces;


import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.User;

import java.util.List;

public interface UserDaoRealizationInf {

    @SuppressWarnings("unused")
    User getUser(long id);

    @SuppressWarnings("unused")
    void deleteUser(long id);

    List<Integer> getAllUserIds();

    int registrationUser(User user) throws DataAccessException;

    Boolean isRegisteredEmail(String email);
}
