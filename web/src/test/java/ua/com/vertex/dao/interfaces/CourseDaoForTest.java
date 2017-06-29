package ua.com.vertex.dao.interfaces;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Course;

@Profile("test")
public interface CourseDaoForTest {

    int createCourse(Course course) throws DataAccessException;

    }
