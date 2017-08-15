package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;
import java.util.Optional;

public interface CourseDaoInf {

    List<Course> getAllCourses(DataNavigator dataNavigator);

    int getQuantityCourses();

    int addCourse(Course course);

    List<Course> getAllCoursesWithDept();

    List<Course> searchCourseByNameAndStatus(String name, boolean isFinished);

    int updateCourseExceptPrice(Course course);

    Optional<Course> getCourseById(int courseId);

    List<User> getUsersAssignedToCourse(int courseId);

    void removeUserFromCourse(DtoCourseUser dto);

    void assignUserToCourse(DtoCourseUser dto);

    List<User> searchForUsersToAssign(DtoCourseUser dto);

}
