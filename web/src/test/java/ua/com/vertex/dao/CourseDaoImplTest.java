package ua.com.vertex.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Course;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CourseDaoInf;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CourseDaoImplTest {

    private final String MSG = "Maybe method was changed";

    @Autowired
    private CourseDaoInf courseDaoInf;

    private Course course;

    @Before
    public void setUp() throws Exception {
        course = new Course.Builder().setId(1).setName("JavaPro").setFinished(false)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(4000)).setTeacherName("Test").setNotes("Test").getInstance();

    }

    @Test
    public void getAllCoursesWithDeptReturnCorrectData() throws Exception {

        List<Course> courses = courseDaoInf.getAllCoursesWithDept();
        assertTrue(MSG, courses.contains(course));
        int index = courses.indexOf(course);
        assertEquals(MSG, course, courses.get(index));
    }

    @Test
    public void updateCourseExceptPriceCorrectUpdate() throws Exception {

        Course courseForUpdate = new Course.Builder().setId(2).setName("JavaStart").setFinished(true)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(8000)).setTeacherName("After update").setNotes("After update").getInstance();

        courseDaoInf.updateCourseExceptPrice(courseForUpdate);

        assertEquals(MSG, courseForUpdate, courseDaoInf.getCourseById(courseForUpdate.getId()).get());

    }

    @Test
    public void getCourseByIdReturnCorrectData() throws Exception {

        assertEquals(MSG,courseDaoInf.getCourseById(course.getId()).get(),course);
    }
}
