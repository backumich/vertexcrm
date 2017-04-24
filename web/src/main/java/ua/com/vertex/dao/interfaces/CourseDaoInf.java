package ua.com.vertex.dao.interfaces;

import ua.com.vertex.beans.Course;

import java.util.List;

public interface CourseDaoInf {
    List<Course> getAllCoursesWithDept();
}
