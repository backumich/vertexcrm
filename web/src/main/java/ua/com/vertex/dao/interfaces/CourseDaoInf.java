package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseDaoInf {

    int addCourse(Course course) throws SQLException;

    int getQuantityCourses() throws SQLException;

    List<Course> getAllCourses(DataNavigator dataNavigator);

    int createCourse(Course course);

    List<Course> getAllCoursesWithDept();

    List<Course> searchCourseByNameAndStatus(Course course);

    int updateCourseExceptPrice(Course course);

    Optional<Course> getCourseById(int courseId);

    List<User> getUsersAssignedToCourse(int courseId);

    void removeUserFromCourse(CourseUserDto dto);

    void assignUserToCourse(CourseUserDto dto);

    List<User> searchForUsersToAssign(CourseUserDto dto);

}
