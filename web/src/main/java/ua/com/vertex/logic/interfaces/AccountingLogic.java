package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.User;

import java.util.List;

public interface AccountingLogic {
    List<User> getCourseUsers(int courseId);
}
