package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
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
    public DataNavigator updateDataNavigator(DataNavigator dataNavigator) throws SQLException {
        LOGGER.debug("Update dataNavigator");

        int dataSize = courseDao.getQuantityCourses();
        int totalPages = (int) Math.ceil((double) dataSize / dataNavigator.getRowPerPage());

        dataNavigator.setDataSize(dataSize);
        if (totalPages == 0 || totalPages < dataNavigator.getCurrentNumberPage()) {
            dataNavigator.setCurrentNumberPage(1);
            dataNavigator.setNextPage(1);
            dataNavigator.setLastPage(1);
            dataNavigator.setTotalPages(1);
        } else {
            dataNavigator.setTotalPages(totalPages);
            dataNavigator.setCurrentNumberPage(dataNavigator.getNextPage());
            dataNavigator.setLastPage(totalPages);
        }

        return dataNavigator;
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
    public List<Course> getAllCoursesWithDept() {
        LOGGER.debug("Call - courseDaoInf.getAllCoursesWithDept()");
        return courseDao.getAllCoursesWithDept();
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDao) {
        this.courseDao = courseDao;
    }

}
