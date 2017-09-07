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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseLogicImplTest {

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
        verify(courseDaoInf, times(1)).getCoursesPerPage(dataNavigator, teacher);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getAllCoursesWithDeptVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.getAllCoursesWithDept()).thenThrow(new DataIntegrityViolationException("Test"));
        courseLogic.getAllCoursesWithDept();
        verify(courseDaoInf, times(1)).getAllCoursesWithDept();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void searchCourseByNameAndStatusVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.searchCourseByNameAndStatus(course.getName(), course.isFinished())).
                thenThrow(new DataIntegrityViolationException("Test"));
        courseDaoInf.searchCourseByNameAndStatus(course.getName(), course.isFinished());
        verify(courseDaoInf, times(1)).searchCourseByNameAndStatus(course.getName(),
                course.isFinished());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateCourseExceptPriceVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.updateCourseExceptPrice(course)).thenThrow(new DataIntegrityViolationException("Test"));
        courseDaoInf.updateCourseExceptPrice(course);
        verify(courseDaoInf, times(1)).updateCourseExceptPrice(course);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getCourseByIdVerifyCourseDaoAndReturnException() throws Exception {
        when(courseDaoInf.getCourseById(anyInt())).thenThrow(new DataIntegrityViolationException("Test"));
        courseDaoInf.getCourseById(anyInt());
        verify(courseDaoInf, times(1)).getCourseById(anyInt());
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
