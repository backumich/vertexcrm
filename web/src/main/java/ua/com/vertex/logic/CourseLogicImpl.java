package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;
import java.util.Optional;

@Service
public class CourseLogicImpl implements CourseLogic {

    private final CourseDaoInf courseDaoInf;

    @Override
    public int getQuantityCourses() {
        return courseDaoInf.getQuantityCourses();
    }

    @Override
    public List<Course> getCoursesPerPages(DataNavigator dataNavigator) {
        return courseDaoInf.getAllCourses(dataNavigator);
    }

    @Override
    public int addCourse(Course course) {
        return courseDaoInf.addCourse(course);
    }

    @Override
    public List<Course> getAllCoursesWithDept() {
        return courseDaoInf.getAllCoursesWithDept();
    }

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) {
        return courseDaoInf.searchCourseByNameAndStatus(course);
    }

    @Override
    public int updateCourseExceptPrice(Course course) {
        return courseDaoInf.updateCourseExceptPrice(course);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) {
        return courseDaoInf.getCourseById(courseId);
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDaoInf) {
        this.courseDaoInf = courseDaoInf;
    }
}
