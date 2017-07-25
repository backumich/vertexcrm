package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseDaoInf {

    int addCourse(Course course) throws SQLException;

    Optional<Course> getCourseById(int courseId);

    List<Course> getCoursesPerPages(DataNavigator dataNavigator);

    int getQuantityCourses() throws SQLException;

    List<Course> getAllCoursesWithDept();
}
