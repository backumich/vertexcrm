package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;
import java.util.Optional;

public interface CourseLogic {

    int getQuantityCourses();

    int getQuantityCourses(User teacher);

    List<Course> getCoursesPerPage(DataNavigator dataNavigator);

    List<Course> getCoursesPerPage(DataNavigator dataNavigator, User teacher);

    int addCourse(Course course);

    List<Course> getAllCoursesWithDept();

    List<Course> searchCourseByNameAndStatus(Course course);

    int updateCourseExceptPrice(Course course);

    Optional<Course> getCourseById(int courseId);

}
