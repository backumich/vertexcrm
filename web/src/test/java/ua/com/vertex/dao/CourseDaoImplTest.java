package ua.com.vertex.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Course;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CourseDaoInf;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class CourseDaoImplTest {

    private final String MSG = "Maybe method was changed";

    @Autowired
    private CourseDaoInf courseDaoInf;

    private Course course;

    @Before
    public void setUp() throws Exception {
        course = new Course.Builder().setId(3).setName("JavaPro").setFinished(false)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(4000)).setTeacherName("Test").setNotes("Test").getInstance();

    }

    @Test
    public void createCourseCorrectInsert() throws Exception {
        int courseId = courseDaoInf.createCourse(course);
        course.setId(courseId);
        assertEquals(MSG, courseDaoInf.getCourseById(courseId).orElse(new Course()), course);
    }

    @Test
    public void getAllCoursesWithDeptReturnCorrectData() throws Exception {
        List<Course> courses = courseDaoInf.getAllCoursesWithDept();
        assertFalse(MSG, courses.isEmpty());
        courses.forEach(course1 -> assertTrue(course1.getPrice().intValue() > 0));
    }

    @Test
    public void searchCourseByNameAndStatusReturnCorrectData() throws Exception {
        Course courseForSearch = new Course.Builder().setName("java").setFinished(true).getInstance();
        List<Course> courses = courseDaoInf.searchCourseByNameAndStatus(courseForSearch);
        courses.forEach(course -> assertTrue(course.getName().contains(courseForSearch.getName())
                && course.isFinished()));
    }

    @Test
    public void searchCourseByNameAndStatusReturnEmptyList() throws Exception {
        Course courseForSearch = new Course.Builder().setName("wwwwwwwwwwww").setFinished(false).getInstance();
        assertTrue(courseDaoInf.searchCourseByNameAndStatus(courseForSearch).isEmpty());
    }

    @Test
    public void updateCourseExceptPriceCorrectUpdate() throws Exception {
        Course courseForUpdate = new Course.Builder().setId(1).setName("JavaStart").setFinished(true)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(8000)).setTeacherName("After update").setNotes("After update").getInstance();

        courseDaoInf.updateCourseExceptPrice(courseForUpdate);
        assertEquals(MSG, courseForUpdate, courseDaoInf.getCourseById(courseForUpdate.getId()).orElse(new Course()));

    }

    @Test
    public void getCourseByIdReturnCorrectData() throws Exception {
        int courseId = courseDaoInf.createCourse(course);
        course.setId(courseId);
        assertEquals(MSG, courseDaoInf.getCourseById(courseId).orElse(new Course()), course);
    }

    @Test
    public void getCourseByIdReturnEmptyOptional() throws Exception {
        assertFalse(MSG, courseDaoInf.getCourseById(33333).isPresent());
    }
}
