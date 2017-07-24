package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseLogic {

    int getQuantityCourses() throws SQLException;

    List<Course> getCoursesPerPages(DataNavigator dataNavigator);

    int addCourse(Course course) throws Exception;

    List<Course> getAllCoursesWithDept() throws Exception;

    List<Course> searchCourseByNameAndStatus(Course course) throws Exception;

    int updateCourseExceptPrice(Course course) throws Exception;

    Optional<Course> getCourseById(int courseId) throws Exception;

}
