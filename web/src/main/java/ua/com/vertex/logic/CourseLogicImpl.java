package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseForOutput;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;

@Service
public class CourseLogicImpl implements CourseLogic {
    private static final Logger LOGGER = LogManager.getLogger(CourseLogicImpl.class);
    private final CourseDaoInf courseDao;

    @Override
    public int getQuantityCourses() throws SQLException {
        return courseDao.getQuantityCourses();
    }

    @Override
    public List<CourseForOutput> getCoursesForOutputPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get part data courses list (dataNavigator)");
        return courseDao.getCoursesForOutputPerPages(dataNavigator);
    }

    @Override
    public int addCourse(Course course) throws Exception {
        return courseDao.addCourse(course);
    }

    @Override
    public List<CourseForOutput> getAllCoursesForOutputWithDept() {
        LOGGER.debug("Call - courseDaoInf.getAllCoursesForOutputWithDept()");
        return courseDao.getAllCoursesForOutputWithDept();
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDao) {
        this.courseDao = courseDao;
    }

}
