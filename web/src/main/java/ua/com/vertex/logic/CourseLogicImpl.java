package ua.com.vertex.logic;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Accounting;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseLogicImpl implements CourseLogic {

    private final CourseDaoInf courseDaoInf;
    private final AccountingDaoInf accountingDaoInf;

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDaoInf, AccountingDaoInf accountingDaoInf) {
        this.courseDaoInf = courseDaoInf;
        this.accountingDaoInf = accountingDaoInf;
    }

    @Override
    public int getQuantityCourses() {
        return courseDaoInf.getQuantityCourses();
    }

    @Override
    public int getQuantityCourses(User teacher) {
        return courseDaoInf.getQuantityCourses(teacher);
    }

    @Override
    public List<Course> getCoursesPerPage(DataNavigator dataNavigator) {
        return courseDaoInf.getCoursesPerPage(dataNavigator);
    }

    @Override
    public List<Course> getCoursesPerPage(DataNavigator dataNavigator, User teacher) {
        return courseDaoInf.getCoursesPerPage(dataNavigator, teacher);
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
    public List<Course> searchCourseByNameAndStatus(String name, boolean isFinished) {
        return courseDaoInf.searchCourseByNameAndStatus(name, isFinished);
    }

    @Override
    public int updateCourseExceptPrice(Course course) {
        return courseDaoInf.updateCourseExceptPrice(course);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) {
        return courseDaoInf.getCourseById(courseId);
    }

    @Override
    public List<User> getUsersAssignedToCourse(int courseId) {
        return courseDaoInf.getUsersAssignedToCourse(courseId);
    }

    @Override
    public void removeUserFromCourse(DtoCourseUser dto) {
        courseDaoInf.removeUserFromCourse(dto);
    }

    @Override
    @Transactional
    public void assignUserToCourse(DtoCourseUser dto) {
        courseDaoInf.assignUserToCourse(dto);
        Course course = courseDaoInf.getCourseById(dto.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("No course with selected id"));
        Accounting accounting = new Accounting(0, dto.getUserId(), course.getId(),
                course.getPrice().doubleValue(), course.getPrice().doubleValue());
        accountingDaoInf.insertAccountingRow(accounting);
    }

    @Override
    public List<User> searchForUsersToAssign(DtoCourseUser dto) {
        return courseDaoInf.searchForUsersToAssign(dto);
    }

}
