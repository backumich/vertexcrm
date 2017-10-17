package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Accounting;
import ua.com.vertex.beans.User;

import java.util.List;

public interface AccountingDaoInf {
    List<User> getCourseUsers(int courseId);

    void updateUserDept(int courseId, int userId, double amount);

    void insertAccountingRow(Accounting accounting);
}
