package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;

@Service
public class CourseLogicImpl implements CourseLogic {
    private static final Logger LOGGER = LogManager.getLogger(CourseLogicImpl.class);
    private final CourseDaoInf courseDao;

    @Override
    public DataNavigator updateDataNavigator(DataNavigator dataNavigator) {
        LOGGER.debug("Update dataNavigator");
        if (dataNavigator.getCurrentNumberPage() == dataNavigator.getNextPage()) {
            dataNavigator.setCurrentNumberPage(1);
            dataNavigator.setNextPage(1);
        } else {
            dataNavigator.setCurrentNumberPage(dataNavigator.getNextPage());
        }
        try {
            int dataSize = courseDao.getQuantityCourses();
            dataNavigator.setDataSize(dataSize);

            final int totalPages = dataSize / dataNavigator.getRowPerPage();
            final int quantityPages = dataNavigator.getQuantityPages();

            dataNavigator.setTotalPages(totalPages >= 0 ? quantityPages + 1 : totalPages);
            dataNavigator.setLastPage(quantityPages);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return dataNavigator;
    }

    @Override
    public List<Course> getCoursesPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get part data courses list (dataNavigator)");
        List<Course> courses = null;
        try {
            courses = courseDao.getAllCourses(dataNavigator);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return courses;
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
