package ua.com.vertex.controllers;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CourseDetailsController.*;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;


@RunWith(MockitoJUnitRunner.class)
public class CourseDetailsControllerTest {

    private final String MSG_INVALID_DATA = "Have wrong objects in model";
    private final String MSG_INVALID_VIEW = "Have wrong viewName in ModelAndView";

    private CourseDetailsController courseDetailsController;
    private Model model;

    @Mock
    private CourseLogic courseLogic;

    @Mock
    private UserLogic userLogic;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        courseDetailsController = new CourseDetailsController(courseLogic, userLogic);
        model = new ExtendedModelMap();
    }

    @Test
    public void searchCourseJspReturnCorrectView() throws Exception {
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.searchCourseJsp().getViewName(), SEARCH_COURSE_JSP);
    }

    @Test
    public void searchCourseJspReturnModelWithEmptyCourse() throws Exception {
        ModelMap map = courseDetailsController.searchCourseJsp().getModelMap();
        assertTrue(MSG_INVALID_DATA, map.containsKey(COURSE_DATA));
        assertEquals(MSG_INVALID_DATA, map.get(COURSE_DATA), new Course());
    }

    @Test
    public void searchCourseReturnCorrectViewWhenBindingResultHasError() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.searchCourse(new Course(), bindingResult, model)
                , SEARCH_COURSE_JSP);
    }

    @Test
    @Ignore
    public void searchCourseHasCorrectDataInModelWhenBindingResultHasError() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        courseDetailsController.searchCourse(new Course(), bindingResult, model);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertEquals(MSG_INVALID_DATA, model.asMap().get(MSG), "Enter the correct data !!! ");

    }

    @Test
    public void searchCourseReturnCorrectViewWhenNotEmptyList() throws Exception {
        when(courseLogic.searchCourseByNameAndStatus("test", true)).
                thenReturn(Collections.singletonList(new Course.Builder().setName("test").
                        setFinished(true).getInstance()));
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.searchCourse(new Course.Builder().setName("test").
                        setFinished(true).getInstance(), bindingResult, model)
                , SEARCH_COURSE_JSP);
    }

    @Test
    public void searchCourseHasCorrectDataInModelWhenNotEmptyList() throws Exception {
        when(courseLogic.searchCourseByNameAndStatus("test", true)).
                thenReturn(Collections.singletonList(new Course.Builder().setName("test").
                        setFinished(true).getInstance()));
        courseDetailsController.searchCourse(new Course.Builder().setName("test").
                setFinished(true).getInstance(), bindingResult, model);
        when(bindingResult.hasErrors()).thenReturn(false);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(COURSES));
        assertEquals(MSG_INVALID_DATA, model.asMap().get(COURSES), Collections.singletonList(new Course.Builder().
                setName("test").setFinished(true).getInstance()));

    }

    @Test
    public void searchCourseReturnCorrectViewWhenEmptyList() throws Exception {
        when(courseLogic.searchCourseByNameAndStatus("test", true)).
                thenReturn(Collections.singletonList(new Course()));
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.searchCourse(new Course(), bindingResult, model)
                , SEARCH_COURSE_JSP);
    }

    @Test
    public void searchCourseHasCorrectDataInModelWhenEmptyList() throws Exception {
        Course course = new Course.Builder().setName("test").getInstance();
        when(courseLogic.searchCourseByNameAndStatus(course.getName(), course.isFinished())).
                thenReturn(new ArrayList<>());
        when(bindingResult.hasErrors()).thenReturn(false);
        courseDetailsController.searchCourse(course, bindingResult, model);
        assertFalse(MSG_INVALID_DATA, model.containsAttribute(COURSES));
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertEquals(MSG_INVALID_DATA, model.asMap().get(MSG),
                String.format("Course with name - '(%s)' not found. " +
                        "Please check the data and try it again.", course.getName()));

    }

    @Test
    public void searchCourseReturnCorrectViewWhenDataAcesException() throws Exception {
        when(courseLogic.searchCourseByNameAndStatus("test", true)).
                thenThrow(new DataIntegrityViolationException("t"));
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.searchCourse(new Course(), bindingResult, model)
                , SEARCH_COURSE_JSP);
    }

    @Test
    public void searchCourseHasCorrectDataInModelWhenDataAcesException() throws Exception {
        when(courseLogic.searchCourseByNameAndStatus("test", true)).
                thenThrow(new DataIntegrityViolationException("t"));
        when(bindingResult.hasErrors()).thenReturn(false);
        courseDetailsController.searchCourse(new Course.Builder().setName("test").
                setFinished(true).getInstance(), bindingResult, model);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertEquals(MSG_INVALID_DATA, model.asMap().get(MSG), LOGGER_SERVER_EXCEPTION);
    }

    @Test
    public void searchCourseReturnCorrectViewWhenException() throws Exception {
        when(courseLogic.searchCourseByNameAndStatus("test", true)).thenThrow(new RuntimeException("test"));
        when(bindingResult.hasErrors()).thenReturn(false);
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.searchCourse(new Course.Builder().setName("test").
                        setFinished(true).getInstance(), bindingResult, model)
                , ERROR);
    }

    @Test
    public void courseDetailsReturnCorrectView() throws Exception {
        when(courseLogic.getCourseById(1)).thenReturn(Optional.of(new Course()));
        when(userLogic.getTeachers()).thenReturn(new HashMap<>());

        assertEquals(MSG_INVALID_VIEW, courseDetailsController.courseDetails(1).getViewName()
                , COURSE_DETAILS_JSP);
    }

    @Test
    public void courseDetailsHasCorrectDataInModel() throws Exception {
        when(courseLogic.getCourseById(1)).thenReturn(Optional.of(new Course()));
        HashMap<Integer, String> teachers = new HashMap<Integer, String>() {
            {
                put(1, "test test 'test@test.com'");
            }
        };
        when(userLogic.getTeachers()).thenReturn(teachers);
        ModelMap result = courseDetailsController.courseDetails(1).getModelMap();

        assertTrue(MSG_INVALID_DATA, result.containsAttribute(COURSE));
        assertTrue(MSG_INVALID_DATA, result.containsAttribute(TEACHERS));
        assertEquals(MSG_INVALID_DATA, result.get(COURSE), new Course());
        assertEquals(MSG_INVALID_DATA, result.get(TEACHERS), teachers);
    }

    @Test
    public void courseDetailsReturnCorrectViewWhenEmptyCourse() throws Exception {
        when(courseLogic.getCourseById(1)).thenReturn(Optional.empty());
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.courseDetails(1).getViewName(), ERROR);
    }

    @Test
    public void courseDetailsHasCorrectDataInModelWhenEmptyCourse() throws Exception {
        when(courseLogic.getCourseById(1)).thenReturn(Optional.empty());
        ModelMap result = courseDetailsController.courseDetails(1).getModelMap();

        assertFalse(MSG_INVALID_DATA, result.containsAttribute(COURSE));
        assertFalse(MSG_INVALID_DATA, result.containsAttribute(TEACHERS));
        assertFalse(MSG_INVALID_DATA, result.containsAttribute(MSG));
    }

    @Test
    public void courseDetailsReturnCorrectViewWhenException() throws Exception {
        when(courseLogic.getCourseById(1)).thenThrow(new RuntimeException("test"));
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.courseDetails(1).getViewName(), ERROR);
    }

    @Test
    public void courseDetailsHasCorrectDataInModelWhenException() throws Exception {
        when(courseLogic.getCourseById(1)).thenThrow(new RuntimeException("test"));
        ModelMap result = courseDetailsController.courseDetails(1).getModelMap();

        assertFalse(MSG_INVALID_DATA, result.containsAttribute(COURSE));
        assertFalse(MSG_INVALID_DATA, result.containsAttribute(TEACHERS));
        assertFalse(MSG_INVALID_DATA, result.containsAttribute(MSG));
    }

    @Test
    public void updateCourseReturnCorrectView() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseLogic.updateCourseExceptPrice(new Course())).thenReturn(1);
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.updateCourse(new Course(), bindingResult, model), ADMIN_JSP);
    }

    @Test
    public void updateCourseHasCorrectDataInModel() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseLogic.updateCourseExceptPrice(new Course.Builder().setId(1).setName("test").
                setFinished(true).getInstance())).thenReturn(1);
        courseDetailsController.updateCourse(new Course.Builder().setId(1).setName("test").
                setFinished(true).getInstance(), bindingResult, model);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertEquals(MSG_INVALID_VIEW, model.asMap().get(MSG), "Course with id - (1) updated.");
    }

    @Test
    public void updateCourseReturnCorrectViewWhenBindingResultHasError() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(courseLogic.updateCourseExceptPrice(new Course())).thenReturn(1);
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.updateCourse(new Course(), bindingResult, model),
                COURSE_DETAILS_JSP);
    }

    @Test
    public void updateCourseHasCorrectDataInModelWhenBindingResultHasError() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        HashMap<Integer, String> teachers = new HashMap<Integer, String>() {
            {
                put(1, "test test 'test@test.com'");
            }
        };
        when(userLogic.getTeachers()).thenReturn(teachers);
        courseDetailsController.updateCourse(new Course(), bindingResult, model);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(TEACHERS));
        assertEquals(MSG_INVALID_DATA, model.asMap().get(TEACHERS), teachers);
    }

    @Test
    public void updateCourseReturnCorrectViewWhenDataAcesException() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseLogic.updateCourseExceptPrice(new Course())).thenThrow(new DataIntegrityViolationException("test"));
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.updateCourse(new Course(), bindingResult, model),
                SEARCH_COURSE_JSP);
    }

    @Test
    public void updateCourseHasCorrectDataInModelWhenDataAcesException() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseLogic.updateCourseExceptPrice(new Course())).thenThrow(new DataIntegrityViolationException("test"));
        courseDetailsController.updateCourse(new Course(), bindingResult, model);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertEquals(MSG_INVALID_DATA, model.asMap().get(MSG), LOGGER_SERVER_EXCEPTION);
    }

    @Test
    public void updateCourseReturnCorrectViewWhenException() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(courseLogic.updateCourseExceptPrice(new Course())).thenThrow(new RuntimeException("test"));
        assertEquals(MSG_INVALID_VIEW, courseDetailsController.updateCourse(new Course(), bindingResult, model),
                ERROR);
    }
}
