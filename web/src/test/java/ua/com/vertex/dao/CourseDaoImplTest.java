package ua.com.vertex.dao;

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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CourseDaoImplTest {

    @Autowired
    private CourseDaoInf courseDaoInf;

    @Test
    public void getAllCoursesWithDeptReturnCorrectData() throws Exception {

        Course course = new Course.Builder().setId(1).setName("JavaPro")
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setFinished(false).setPrice(BigDecimal.valueOf(4000)).setTeacherName("Test").setNotes("Test").getInstance();

        assertEquals("Maybe method was changed", courseDaoInf.getAllCoursesWithDept(), course);
    }
}
