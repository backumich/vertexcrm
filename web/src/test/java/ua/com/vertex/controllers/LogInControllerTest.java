package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.LoggingLogicImpl;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.EmailExtractor;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LogInControllerTest {

    @Mock
    private UserLogic userLogic;

    @InjectMocks
    private LoggingLogicImpl loggingLogic;

    @Mock
    private Model model;

    @Mock
    private EmailExtractor emailExtractor;
    private MockMvc mockMvc;
    private LogInController controller;
    private User user;

    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "userProfile";
    private static final String EMAIL = "test@test.com";

    @Before
    public void setUp() {
        controller = new LogInController(loggingLogic, emailExtractor);
        user = new User.Builder().getInstance();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showLogInPageForLoggedInUserReturnsUserView() throws Exception {
        when(emailExtractor.getEmailFromAuthentication()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(Optional.ofNullable(user));

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(USER_PAGE))
                .build();
        mockMvc.perform(get("/logIn"))
                .andExpect(view().name(USER_PAGE));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void showLogInPageForLoggedInUserReturnsAdminView() throws Exception {
        when(emailExtractor.getEmailFromAuthentication()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(Optional.ofNullable(user));

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(ADMIN_PAGE))
                .build();
        mockMvc.perform(get("/logIn"))
                .andExpect(view().name(ADMIN_PAGE));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showLogInPageForLoggedInUserAddsModelAttributes() throws Exception {
        when(emailExtractor.getEmailFromAuthentication()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(Optional.ofNullable(user));

        controller.showLogInPage(model);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void showLoggedInPageForLoggedInUserReturnsUserView() throws Exception {
        when(emailExtractor.getEmailFromAuthentication()).thenReturn("user");
        when(userLogic.getUserByEmail("user")).thenReturn(Optional.ofNullable(user));

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(USER_PAGE))
                .build();
        mockMvc.perform(get("/loggedIn"))
                .andExpect(view().name(USER_PAGE));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void showLoggedInPageForLoggedInUserReturnsAdminView() throws Exception {
        when(emailExtractor.getEmailFromAuthentication()).thenReturn("user");
        when(userLogic.getUserByEmail("user")).thenReturn(Optional.ofNullable(user));

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(ADMIN_PAGE))
                .build();
        mockMvc.perform(get("/loggedIn"))
                .andExpect(view().name(ADMIN_PAGE));
    }
}
