package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.List;
import java.util.Optional;

@Service
public class CourseLogicImpl implements CourseLogic {

    private final CourseDaoInf courseDaoInf;
    private static final Logger LOGGER = LogManager.getLogger(CourseLogicImpl.class);

    @Override
    public List<Course> getAllCoursesWithDept() {
        LOGGER.debug("Call - courseDaoInf.getAllCoursesWithDept()");
        return courseDaoInf.getAllCoursesWithDept();
    }

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) {
        LOGGER.debug(String.format("Call courseDaoInf.searchCourseByNameAndStatus(%s)",course));
        return courseDaoInf.searchCourseByNameAndStatus(course);
    }

    @Override
    public int updateCourseExceptPrice(Course course) {
        LOGGER.debug(String.format("Call courseDaoInf.updateCourseExceptPrice(%s)",course));
        return courseDaoInf.updateCourseExceptPrice(course);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) {
        LOGGER.debug(String.format("Call courseDaoInf.getCourseById(%s)",courseId));
        return courseDaoInf.getCourseById(courseId);
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDaoInf) {
        this.courseDaoInf = courseDaoInf;
    }
}
