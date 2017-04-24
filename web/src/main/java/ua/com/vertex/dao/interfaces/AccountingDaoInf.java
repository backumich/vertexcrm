package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Payment;
import ua.com.vertex.beans.User;

import java.util.List;

public interface AccountingDaoInf {
    List<User> getCourseUsers(int courseId);

    int updateUserDept(int courseId, int userId, Payment payment);
}
