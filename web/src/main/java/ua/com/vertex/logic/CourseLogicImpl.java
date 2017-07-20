package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseLogicImpl implements CourseLogic {
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
        LOGGER.debug("Call - courseDaoInf.getAllCoursesWithDept()");
        return courseDao.getAllCoursesWithDept();
    }

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) throws Exception {
        LOGGER.debug(String.format("Call courseDaoInf.searchCourseByNameAndStatus(%s)",course));
        return courseDao.searchCourseByNameAndStatus(course);
    }

    @Override
    public int updateCourseExceptPrice(Course course) throws Exception {
        LOGGER.debug(String.format("Call courseDaoInf.updateCourseExceptPrice(%s)",course));
        return courseDao.updateCourseExceptPrice(course);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws Exception {
        LOGGER.debug(String.format("Call courseDaoInf.getCourseById(%s)",courseId));
        return courseDao.getCourseById(courseId);
    }

    @Override
    public List<User> getUsersAssignedToCourse(int courseId) {
        return courseDao.getUsersAssignedToCourse(courseId);
    }

    @Override
    public void removeUserFromCourse(CourseUserDto dto) {
        courseDao.removeUserFromCourse(dto);
    }

    @Override
    public void assignUserToCourse(CourseUserDto dto) {
        courseDao.assignUserToCourse(dto);
    }

    @Override
    public List<User> searchForUsersToAssign(CourseUserDto dto) {
        return courseDao.searchForUsersToAssign(dto);
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDao) {
        this.courseDao = courseDao;
    }

}
