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
    public List<Course> getCoursesPerPages(DataNavigator dataNavigator) {
        List<Course> courses = null;
        if (dataNavigator.getCurrentNumberPage() == dataNavigator.getNextPage()) {
            dataNavigator.setCurrentNumberPage(1);
            dataNavigator.setNextPage(1);
        } else {
            dataNavigator.setCurrentNumberPage(dataNavigator.getNextPage());
        }

        try {
            courses = courseDao.getAllCourses(dataNavigator);
            int dataSize = courseDao.getQuantityCourses();

            dataNavigator.setDataSize(dataSize);
            dataNavigator.setQuantityPages(dataSize / dataNavigator.getCurrentRowPerPage());
            if (dataSize / dataNavigator.getCurrentRowPerPage() >= 0) {
                dataNavigator.setQuantityPages(dataNavigator.getQuantityPages() + 1);
            }
            dataNavigator.setLastPage(dataNavigator.getQuantityPages());
        } catch (SQLException e) {
            LOGGER.warn(e);
        }
        return courses;
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDao) {
        this.courseDao = courseDao;
    }
}
