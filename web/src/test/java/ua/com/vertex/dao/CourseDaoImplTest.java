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
import ua.com.vertex.dao.interfaces.CourseDaoForTest;
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
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Autowired
    private CourseDaoForTest courseDaoForTest;

    private Course course;

    @Before
    public void setUp() throws Exception {
        course = new Course.Builder().setId(3).setName("JavaPro").setFinished(false)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(4000)).setTeacher(new User.Builder().setUserId(10).getInstance())
                .setNotes("Test").getInstance();

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
                .setPrice(BigDecimal.valueOf(8000)).setTeacher(new User.Builder().setUserId(22).getInstance())
                .setNotes("After update").getInstance();

        courseDaoInf.updateCourseExceptPrice(courseForUpdate);
        assertEquals(MSG, courseForUpdate, courseDaoInf.getCourseById(courseForUpdate.getId()).orElse(new Course()));

    }

    @Test
    public void getCourseByIdReturnCorrectData() throws Exception {
        int courseId = courseDaoForTest.insertCourse(course);
        course.setId(courseId);
        assertEquals(MSG, courseDaoInf.getCourseById(courseId).orElse(new Course()), course);
    }

    @Test
    public void getCourseByIdReturnEmptyOptional() throws Exception {
        assertFalse(MSG, courseDaoInf.getCourseById(33333).isPresent());
    }

    @Test
    public void getCoursesById() throws Exception {
        if (courseDaoInf.getCourseById(111).isPresent()) {
            assertEquals(MSG, new Course.Builder()
                    .setId(111)
                    .setName("Super JAVA")
                    .setStart(LocalDate.parse("2017-04-01"))
                    .setFinished(false)
                    .setPrice(BigDecimal.valueOf(999999.99))
                    .setTeacherName("Yo Ho Ho")
                    .setSchedule("Sat, Sun")
                    .setNotes("Welcome, we don't expect you (=")
                    .getInstance(), courseDaoInf.getCourseById(111).get());
        }
    }

    @Test
    public void getQuantityCoursesReturnNotNull() throws Exception {
        int result = courseDaoInf.getQuantityCourses();
        assertNotNull(MSG, result);
    }
}
