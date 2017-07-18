package ua.com.vertex.dao.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDaoInf {

    List<Course> getAllCoursesWithDept() throws DataAccessException;

    List<Course> searchCourseByNameAndStatus(Course course) throws DataAccessException;

    int updateCourseExceptPrice(Course course) throws DataAccessException;

    Optional<Course> getCourseById(int courseId) throws DataAccessException;

}
