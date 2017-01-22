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
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Storage;

import java.util.Optional;

import static org.mockito.Mockito.*;
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

    @Mock
    private UserLogic userLogic;

    @Mock
    private Model model;

    private MockMvc mockMvc;
    private LogInController controller;

    private static final int EXISTING_USER_ID = 22;
    private static final String ADMIN_PAGE = "admin";
    private static final String USER_PAGE = "userProfile";
    private static final String EMAIL = "test@test.com";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new LogInController(storage, userLogic);
    }

    @SuppressWarnings("Duplicates")
    @Test
    @WithMockUser(authorities = "USER")
    public void showLogInPageForLoggedInUserReturnsUserView() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_USER_ID).getInstance();
        Optional<User> optional = Optional.ofNullable(user);

        when(storage.getEmail()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(optional);

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(USER_PAGE))
                .build();
        mockMvc.perform(get("/logIn"))
                .andExpect(view().name(USER_PAGE));
    }


    @SuppressWarnings("Duplicates")
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showLogInPageForLoggedInUserReturnsAdminView() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_USER_ID).getInstance();
        Optional<User> optional = Optional.ofNullable(user);

        when(storage.getEmail()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(optional);

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(ADMIN_PAGE))
                .build();
        mockMvc.perform(get("/logIn"))
                .andExpect(view().name(ADMIN_PAGE));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void showLogInPageForLoggedInUserAddsModelAttributes() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_USER_ID).getInstance();
        Optional<User> optional = Optional.ofNullable(user);

        when(storage.getEmail()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(optional);

        controller.showLogInPage(model);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    @WithMockUser(authorities = "NONE")
    public void showLogInPageForLoggedInUserReturnsErrorIfWrongAuthorities() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_USER_ID).getInstance();
        Optional<User> optional = Optional.ofNullable(user);

        when(storage.getEmail()).thenReturn(EMAIL);
        when(userLogic.getUserByEmail(EMAIL)).thenReturn(optional);

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("error"))
                .build();
        mockMvc.perform(get("/logIn"))
                .andExpect(view().name("error"));
    }

    @Test
    @WithMockUser(username = EMAIL)
    public void showLoggedInPageForLoggedInUserSetsEmail() {
        when(storage.getEmail()).thenReturn(null);

        controller.showLoggedIn(model);
        verify(storage, times(1)).setEmail(EMAIL);
    }

    @SuppressWarnings("Duplicates")
    @Test
    @WithMockUser(authorities = "USER")
    public void showLoggedInPageForLoggedInUserReturnsUserView() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_USER_ID).getInstance();
        Optional<User> optional = Optional.ofNullable(user);

        when(storage.getEmail()).thenReturn("user");
        when(userLogic.getUserByEmail("user")).thenReturn(optional);

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(USER_PAGE))
                .build();
        mockMvc.perform(get("/loggedIn"))
                .andExpect(view().name(USER_PAGE));
    }

    @SuppressWarnings("Duplicates")
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showLoggedInPageForLoggedInUserReturnsAdminView() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_USER_ID).getInstance();
        Optional<User> optional = Optional.ofNullable(user);

        when(storage.getEmail()).thenReturn("user");
        when(userLogic.getUserByEmail("user")).thenReturn(optional);

        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(ADMIN_PAGE))
                .build();
        mockMvc.perform(get("/loggedIn"))
                .andExpect(view().name(ADMIN_PAGE));
    }
}
