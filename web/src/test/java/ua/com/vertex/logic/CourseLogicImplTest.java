package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseLogicImplTest {

    private final String EXCEPTION_MSG = "Course logic didn't call corresponding course dao method";

    @Mock
    private CourseDaoInf courseDaoInf;

    private CourseLogic courseLogic;

    private Course course;

    @Before
    public void setUp() {
        courseLogic = new CourseLogicImpl(courseDaoInf);
        course = new Course.Builder().setId(1).setName("test").setFinished(false).setPrice(new BigDecimal(10000)).
                setStart(LocalDate.of(2017, 5, 28)).setNotes("test").
                setNotes("test").getInstance();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getCoursesPerPageVerifyCourseDaoAndReturnException() throws Exception {
        DataNavigator dataNavigator = new DataNavigator();
        User teacher = new User.Builder().setUserId(7).getInstance();

        when(courseDaoInf.getCoursesPerPage(dataNavigator, teacher))
                .thenThrow(new DataIntegrityViolationException("Test"));
        courseLogic.getCoursesPerPage(dataNavigator, teacher);
        fail(EXCEPTION_MSG);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getAllCoursesWithDeptVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.getAllCoursesWithDept()).thenThrow(new DataIntegrityViolationException("Test"));
        courseLogic.getAllCoursesWithDept();
        fail(EXCEPTION_MSG);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void searchCourseByNameAndStatusVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.searchCourseByNameAndStatus(course.getName(), course.isFinished())).
                thenThrow(new DataIntegrityViolationException("Test"));
        courseDaoInf.searchCourseByNameAndStatus(course.getName(), course.isFinished());
        fail(EXCEPTION_MSG);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateCourseExceptPriceVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.updateCourseExceptPrice(course)).thenThrow(new DataIntegrityViolationException("Test"));
        courseDaoInf.updateCourseExceptPrice(course);
        fail(EXCEPTION_MSG);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getCourseByIdVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.getCourseById(anyInt())).thenThrow(new DataIntegrityViolationException("Test"));
        courseDaoInf.getCourseById(anyInt());
        fail(EXCEPTION_MSG);
    }

    @Test
    public void getUsersAssignedToCourseInvokesDao() {
        courseLogic.getUsersAssignedToCourse(anyInt());
        verify(courseDaoInf, times(1)).getUsersAssignedToCourse(anyInt());
    }

    @Test
    public void removeUserFromCourseInvokesDao() {
        DtoCourseUser dto = new DtoCourseUser();
        courseLogic.removeUserFromCourse(dto);
        verify(courseDaoInf, times(1)).removeUserFromCourse(dto);
    }

    @Test
    public void assignUserToCourseInvokesDao() {
        DtoCourseUser dto = new DtoCourseUser();
        courseLogic.assignUserToCourse(dto);
        verify(courseDaoInf, times(1)).assignUserToCourse(dto);
    }

    @Test
    public void searchForUsersToAssignInvokesDao() {
        DtoCourseUser dto = new DtoCourseUser();
        courseLogic.searchForUsersToAssign(dto);
        verify(courseDaoInf, times(1)).searchForUsersToAssign(dto);
    }
}
