package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseForOutput;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;

public interface CourseLogic {
    int getQuantityCourses() throws SQLException;

    List<CourseForOutput> getCoursesForOutputPerPages(DataNavigator dataNavigator);

    int addCourse(Course course) throws Exception;

    List<Course> getAllCoursesWithDept();

}
