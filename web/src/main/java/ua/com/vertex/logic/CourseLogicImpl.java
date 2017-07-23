package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseLogicImpl implements CourseLogic {

    private final CourseDaoInf courseDaoInf;
    private static final Logger LOGGER = LogManager.getLogger(CourseLogicImpl.class);
    private final CourseDaoInf courseDao;

    @Override
    public int getQuantityCourses() throws SQLException {
        return courseDao.getQuantityCourses();
    }

    @Override
    public List<Course> getCoursesPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get part data courses list (dataNavigator)");
        return courseDao.getAllCourses(dataNavigator);
    }

    @Override
    public int addCourse(Course course) throws Exception {
        return courseDao.addCourse(course);
    }

    @Override
    public List<Course> getAllCoursesWithDept() throws Exception {
        return courseDaoInf.getAllCoursesWithDept();
    }

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) throws Exception {
        return courseDaoInf.searchCourseByNameAndStatus(course);
    }

    @Override
    public int updateCourseExceptPrice(Course course) throws Exception {
        return courseDaoInf.updateCourseExceptPrice(course);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws Exception {
        return courseDaoInf.getCourseById(courseId);
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDaoInf) {
        this.courseDaoInf = courseDaoInf;
    }
}
