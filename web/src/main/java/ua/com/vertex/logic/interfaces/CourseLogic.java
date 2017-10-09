package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;

public interface CourseLogic {

    int getQuantityCourses();

    List<Course> getCoursesPerPages(DataNavigator dataNavigator);

    int addCourse(Course course);

    List<Course> getAllCoursesWithDept();

    List<Course> searchCourseByNameAndStatus(String name, boolean isFinished);

    int updateCourseExceptPrice(Course course);

    Course getCourseById(int courseId);

    List<User> getUsersAssignedToCourse(int courseId);

    void removeUserFromCourse(DtoCourseUser dto);

    void assignUserToCourse(DtoCourseUser dto);

    List<User> searchForUsersToAssign(DtoCourseUser dto);

}
