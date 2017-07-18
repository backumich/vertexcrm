package ua.com.vertex.dao.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.User;

import java.util.List;

public interface AccountingDaoInf {
    List<User> getCourseUsers(int courseId) throws DataAccessException;

    void updateUserDept(int courseId, int userId, double amount) throws DataAccessException;
}
