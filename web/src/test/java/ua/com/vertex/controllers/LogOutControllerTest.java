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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LogOutControllerTest {
    @Mock
    private Storage storage;

    private LogOutController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new LogOutController(storage);
    }

    @Test
    @WithMockUser
    public void showLogOutPageForLoggedInUserShouldReturnCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("logOut"))
                .build();
        mockMvc.perform(get("/logOut"))
                .andExpect(view().name("logOut"));
    }

    @Test
    public void showLogOutPageForLoggedOutUserShouldReturnCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("loggedOut"))
                .build();
        mockMvc.perform(get("/loggedOut"))
                .andExpect(view().name("loggedOut"));
    }

    @Test
    public void sessionIdShouldBeSet() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(storage.getSessionId()).thenReturn(null);
        when(storage.getCount()).thenReturn(3L);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getId()).thenReturn("testSessionId");

        controller.processLogOut(request);
        verify(storage).setSessionId("testSessionId");
    }

    @Test
    public void logOutRefuseShouldReturnCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("logOut"))
                .build();
        mockMvc.perform(get("/logOutRefuse"))
                .andExpect(view().name("index"));
    }
}
