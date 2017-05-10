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
import ua.com.vertex.logic.interfaces.UserLogic;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.TreeMap;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class ViewAllUsersControllerTest {

    @Autowired
    private UserLogic logic;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void viewAllUsersControllerReturnedViewTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new ViewAllUsersController(logic))
                .setSingleView(new InternalResourceView("viewAllUsers"))
                .build();
        mockMvc.perform(get("/viewAllUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAllUsers"))
                .andExpect(forwardedUrl("viewAllUsers"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(1))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(10))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(22))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(33))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(44))))))
                .andExpect(model().attributeExists("viewAllUsers"))
                .andExpect(model().attribute("viewAllUsers", hasProperty("currentNamePage", is("viewAllUsers"))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("currentRowPerPage", is(25))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("quantityPages", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("dataSize", is(5))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }

    @Test
    public void viewAllUsersControllerReloadTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(new ViewAllUsersController(logic))
                .setSingleView(new InternalResourceView("viewAllUsers"))
                .build();
        mockMvc.perform(post("/viewAllUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAllUsers"))
                .andExpect(forwardedUrl("viewAllUsers"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(1))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(10))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(22))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(33))))))
                .andExpect(model().attribute("users", hasItem(allOf(hasProperty("userId", is(44))))))
                .andExpect(model().attributeExists("viewAllUsers"))
                .andExpect(model().attribute("viewAllUsers", hasProperty("currentNamePage", is("viewAllUsers"))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("currentNumberPage", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("nextPage", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("lastPage", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("currentRowPerPage", is(25))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("quantityPages", is(1))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("dataSize", is(5))))
                .andExpect(model().attribute("viewAllUsers", hasProperty("countRowPerPage", is(new TreeMap<Integer, Integer>() {{
                    put(25, 25);
                    put(50, 50);
                    put(100, 100);
                }}))));
    }
}
