package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.utils.Storage;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LogInControllerTest {

    @Mock
    private Storage storage;

    private MockMvc mockMvc;
    private LogInController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new LogInController(storage);
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void showLogInPageForLoggedInUserShouldReturnCorrectView() throws Exception {
        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("user"))
                .build();
        mockMvc.perform(get("/logIn"))
                .andExpect(view().name("user"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void showLoggedInPageForLoggedInUserShouldReturnCorrectView() throws Exception {
        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("user"))
                .build();
        mockMvc.perform(get("/loggedIn"))
                .andExpect(view().name("user"));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    public void emailShouldBeSet() {
        when(storage.getEmail()).thenReturn(null);

        controller.showLoggedIn();
        verify(storage).setEmail("test@test.com");
    }
}
