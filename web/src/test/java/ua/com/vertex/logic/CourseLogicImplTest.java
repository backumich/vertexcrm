package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import ua.com.vertex.beans.Accounting;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseLogicImplTest {

    private final String EXCEPTION_MSG = "Course logic didn't call corresponding course dao method";

    private final int COURSE_ID = 2;
    private final int USER_ID = 3;

    @Mock
    private CourseDaoInf courseDaoInf;

    @Mock
    private AccountingDaoInf accountingDaoInf;

    private CourseLogic courseLogic;

    private Course course;
    private DtoCourseUser dto;
    private Accounting accounting;

    @Before
    public void setUp() {
        courseLogic = new CourseLogicImpl(courseDaoInf, accountingDaoInf);
        course = new Course.Builder().setId(1).setName("test").setFinished(false).setPrice(new BigDecimal(10000)).
                setStart(LocalDate.of(2017, 5, 28)).setNotes("test").
                setNotes("test").getInstance();
        dto = new DtoCourseUser();
        dto.setCourseId(COURSE_ID);
        dto.setUserId(USER_ID);
        accounting = new Accounting.Builder().setUserId(USER_ID).setCourseId(COURSE_ID).setCourseCoast(8000d)
                .setDept(8000d).getInstance();
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
        courseLogic.removeUserFromCourse(dto);
        verify(courseDaoInf, times(1)).removeUserFromCourse(dto);
    }

    @Test
    public void assignUserToCourseInvokesDao() {
        when(courseDaoInf.getCourseById(COURSE_ID)).thenReturn(Optional.ofNullable(
                new Course.Builder().setId(COURSE_ID).setPrice(new BigDecimal(8000)).getInstance()));

        courseLogic.assignUserToCourse(dto);
        verify(courseDaoInf, times(1)).assignUserToCourse(dto);
        verify(accountingDaoInf, times(1)).insertAccountingRow(accounting);
    }

    @Test(expected = NoSuchElementException.class)
    public void assignUserToCourseReturnException() {
        when(courseDaoInf.getCourseById(anyInt())).thenReturn(Optional.empty());

        courseLogic.assignUserToCourse(dto);
        fail("We found course with id = 0!");
    }

    @Test
    public void searchForUsersToAssignInvokesDao() {
        DtoCourseUser dto = new DtoCourseUser();
        courseLogic.searchForUsersToAssign(dto);
        verify(courseDaoInf, times(1)).searchForUsersToAssign(dto);
    }
}
