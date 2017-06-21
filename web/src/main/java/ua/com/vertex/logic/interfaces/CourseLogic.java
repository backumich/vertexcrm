package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.Course;

import java.util.List;
import java.util.Optional;

public interface CourseLogic {
    List<Course> getAllCoursesWithDept() throws Exception;

    List<Course> searchCourseByNameAndStatus(Course course) throws Exception;

    int updateCourseExceptPrice(Course course) throws Exception;

    Optional<Course> getCourseById(int courseId) throws Exception;

}
