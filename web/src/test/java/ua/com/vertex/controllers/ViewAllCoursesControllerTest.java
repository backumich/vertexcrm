package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.TreeMap;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class ViewAllCoursesControllerTest {
    @Autowired
    private CourseLogic logic;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void viewAllCoursesTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new ViewAllCoursesController(logic))
                .setSingleView(new InternalResourceView("viewAllCourses"))
                .build();
        mockMvc.perform(get("/viewAllCourses"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAllCourses"))
                .andExpect(forwardedUrl("viewAllCourses"))
                .andExpect(model().attributeExists("courses"))
                //.andExpect(model().attribute("courses", hasSize(2)))
                .andExpect(model().attribute("courses", hasItem(allOf(hasProperty("id", is(1))))))
                .andExpect(model().attribute("courses", hasItem(allOf(hasProperty("id", is(3))))))

                .andExpect(model().attributeExists("viewAllCourses"))
                .andExpect(model().attribute("viewAllCourses", hasProperty("currentNamePage", is("viewAllCourses"))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("currentRowPerPage", is(25))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("quantityPages", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("dataSize", is(3))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }

    @Test
    public void reloadTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new ViewAllCoursesController(logic))
                .setSingleView(new InternalResourceView("viewAllCourses"))
                .build();
        mockMvc.perform(post("/viewAllCourses"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAllCourses"))
                .andExpect(forwardedUrl("viewAllCourses"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("courses", hasSize(3)))
                .andExpect(model().attribute("courses", hasItem(allOf(hasProperty("id", is(1))))))
                .andExpect(model().attribute("courses", hasItem(allOf(hasProperty("id", is(2))))))

                .andExpect(model().attributeExists("viewAllCourses"))
                .andExpect(model().attribute("viewAllCourses", hasProperty("currentNamePage", is("viewAllCourses"))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("currentRowPerPage", is(25))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("quantityPages", is(1))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("dataSize", is(3))))
                .andExpect(model().attribute("viewAllCourses", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }
}