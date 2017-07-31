package ua.com.vertex.dao.interfaces;

import org.springframework.context.annotation.Profile;
import ua.com.vertex.beans.Course;

@Profile("test")
public interface CourseDaoForTest {

    int insertCourse(Course course);

}
