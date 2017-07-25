package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class AddCourseControllerTest {
    @Autowired
    private CourseLogic logic;

    @Autowired
    private UserLogic userLogic;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addCourseFirstAppealTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new AddCourseController(logic, userLogic))
                .setSingleView(new InternalResourceView("addCourse"))
                .build();
        mockMvc.perform(get("/addCourse"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCourse"))
                .andExpect(forwardedUrl("addCourse"));
    }

    @Test
    @Ignore
    public void addCourseTest() throws Exception {
        String name = "Test course name";
        String start = "2001-01-01";
        String price = "1";

        MockMvc mockMvc = standaloneSetup(new AddCourseController(logic, userLogic))
                .setSingleView(new InternalResourceView("/viewAllCourses"))
                .build();
        mockMvc.perform(post("/addCourse")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("start", start)
                .param("price", price)
                .sessionAttr("course", new Course()))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/viewAllCourses"))
                .andExpect(forwardedUrl("/viewAllCourses"))
        ;
    }
}
