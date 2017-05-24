package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;

public interface CourseDaoInf {
    List<Course> getAllCourses(DataNavigator dataNavigator);

    int getQuantityCourses() throws SQLException;
}
