package ua.com.vertex.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.EmailExtractor;

import java.util.TreeMap;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class ViewCoursesControllerTest {
    @Autowired
    private CourseLogic logic;

    @Autowired
    private UserLogic userLogic;

    @Mock
    private EmailExtractor emailExtractor;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void viewAllCoursesTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new ViewCoursesController(logic, userLogic, emailExtractor))
                .setSingleView(new InternalResourceView("viewCourses"))
                .build();
        mockMvc.perform(get("/viewCourses/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewCourses"))
                //.andExpect(forwardedUrl("/viewCourses/all"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("courses", hasItem(hasProperty("id", is(111)))))
                .andExpect(model().attribute("courses", hasItem(hasProperty("id", is(222)))))

                .andExpect(model().attributeExists("viewCourses"))
                .andExpect(model().attribute("viewCourses", hasProperty("currentNamePage", is("viewCourses/all"))))
                .andExpect(model().attribute("viewCourses", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("rowPerPage", is(25))))
                .andExpect(model().attribute("viewCourses", hasProperty("totalPages", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }

    @Test
    public void reloadTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new ViewCoursesController(logic, userLogic, emailExtractor))
                .setSingleView(new InternalResourceView("viewCourses"))
                .build();
        mockMvc.perform(post("/viewCourses/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewCourses"))
                //.andExpect(forwardedUrl("/viewCourses/all"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("courses", hasItem(hasProperty("id", is(111)))))
                .andExpect(model().attribute("courses", hasItem(hasProperty("id", is(222)))))

                .andExpect(model().attributeExists("viewCourses"))
                .andExpect(model().attribute("viewCourses", hasProperty("currentNamePage", is("viewCourses/all"))))
                .andExpect(model().attribute("viewCourses", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("rowPerPage", is(25))))
                .andExpect(model().attribute("viewCourses", hasProperty("totalPages", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }

    @Test
    public void viewTeacherCoursesTest() throws Exception {
        //when(certificateLogic.getAllCertificatesByUserEmail("test")).thenReturn(certificates);
        when(emailExtractor.getEmailFromAuthentication()).thenReturn("teacher@test.com");

        MockMvc mockMvc = standaloneSetup(new ViewCoursesController(logic, userLogic, emailExtractor))
                .setSingleView(new InternalResourceView("viewCourses"))
                .build();
        mockMvc.perform(get("/viewCourses/teacher"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewCourses"))
                //.andExpect(forwardedUrl("/viewCourses/teacher"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("courses", hasItem(hasProperty("id", is(7)))))

                .andExpect(model().attributeExists("viewCourses"))
                .andExpect(model().attribute("viewCourses", hasProperty("currentNamePage", is("viewCourses/teacher"))))
                .andExpect(model().attribute("viewCourses", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("rowPerPage", is(25))))
                .andExpect(model().attribute("viewCourses", hasProperty("totalPages", is(1))))
                .andExpect(model().attribute("viewCourses", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }

}