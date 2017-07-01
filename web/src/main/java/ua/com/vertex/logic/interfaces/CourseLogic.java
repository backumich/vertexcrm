package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface CourseLogic {
    List<Course> getAllCoursesWithDept() throws Exception;

    List<Course> searchCourseByNameAndStatus(Course course) throws Exception;

    int updateCourseExceptPrice(Course course) throws Exception;

    Optional<Course> getCourseById(int courseId) throws Exception;

    List<User> getUsersAssignedToCourse(int courseId);

    void removeUserFromCourse(CourseUserDto dto);

    void assignUserToCourse(CourseUserDto dto);

    List<User> searchForUsersToAssign(CourseUserDto dto);
}
