package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.context.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LogOutControllerTest {

    private MockMvc mockMvc;
    private LogOutController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new LogOutController();
    }

    @Test
    @WithMockUser
    public void showLogOutPageForLoggedInUserReturnsCorrectView() throws Exception {
        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("logOut"))
                .build();
        mockMvc.perform(get("/logOut"))
                .andExpect(view().name("logOut"));
    }

    @Test
    public void logOutRefuseReturnsCorrectView() throws Exception {
        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("logOut"))
                .build();
        mockMvc.perform(get("/logOutRefuse"))
                .andExpect(view().name("index"));
    }
}
