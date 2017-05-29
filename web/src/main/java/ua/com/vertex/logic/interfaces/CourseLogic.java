package ua.com.vertex.logic.interfaces;


import ua.com.vertex.beans.Course;

import java.util.List;

public interface CourseLogic {
    List<Course> getAllCoursesWithDept();

    List<Course> searchCourseByNameAndStatus(Course course);

    int updateCourseExceptPrice(Course course);

}
