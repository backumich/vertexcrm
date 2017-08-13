package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;
import java.util.Optional;

public interface CourseLogic {

    int getQuantityCourses();

    List<Course> getCoursesPerPages(DataNavigator dataNavigator);

    int addCourse(Course course);

    List<Course> getAllCoursesWithDept();

    List<Course> searchCourseByNameAndStatus(String name, boolean isFinished);

    int updateCourseExceptPrice(Course course);

    Optional<Course> getCourseById(int courseId);

    List<User> getUsersAssignedToCourse(int courseId);

    void removeUserFromCourse(CourseUserDto dto);

    void assignUserToCourse(CourseUserDto dto);

    List<User> searchForUsersToAssign(CourseUserDto dto);

}
