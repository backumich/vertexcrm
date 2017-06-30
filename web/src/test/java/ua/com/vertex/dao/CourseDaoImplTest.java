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
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.utils.DataNavigator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import ua.com.vertex.beans.Course;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CourseDaoInf;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

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
    private User user1;
    private User user3;

    @Before
    public void setUp() throws Exception {
        course = new Course.Builder().setId(3).setName("JavaPro").setFinished(false)
                .setStart(LocalDate.of(2017, 2, 1))
                .setPrice(new BigDecimal("4000.00")).setTeacherName("Test").setNotes("Test").getInstance();
        user1 = new User.Builder()
                .setEmail("user1@email.com")
                .setFirstName("Name1")
                .setLastName("Surname1")
                .setPhone("+38050 111 1111")
                .getInstance();
        user3 = new User.Builder()
                .setEmail("user3@email.com")
                .setFirstName("Name3")
                .setLastName("Surname3")
                .setPhone("+38050 333 3333")
                .getInstance();
    }

    @Test
    public void addCourseCorrectInsert() throws Exception {
        int courseId = courseDaoInf.addCourse(course);
        course.setId(courseId);
        assertEquals(MSG, courseDaoInf.getCourseById(courseId).orElse(new Course()), course);
    }

    @Test
    public void addCourseIncorrectInsert() throws Exception {
        int courseId = courseDaoInf.addCourse(course);
        course.setId(courseId);
        assertNotEquals(MSG, courseDaoInf.getCourseById(-1).orElse(new Course()), course);
    }

    @Test
    public void getAllCourses() throws Exception {
        DataNavigator dataNavigator = new DataNavigator();

        List<Course> courses = courseDaoInf.getAllCourses(dataNavigator);
        assertFalse(MSG, courses.isEmpty());

        courses.forEach(course1 -> {
            assertTrue(course1.getId() > 0);
            assertTrue(course1.getName().length() > 5 && course1.getName().length() < 256);
        });
    }

    @Test
    public void getAllCoursesWithDeptReturnCorrectData() throws Exception {

        Course course = new Course.Builder().setId(1).setName("JavaPro")
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setFinished(false).setPrice(BigDecimal.valueOf(4000)).setTeacherName("Test").setNotes("Test").getInstance();

        assertTrue("Maybe method was changed", courseDaoInf.getAllCoursesWithDept().contains(course));
    }
}
