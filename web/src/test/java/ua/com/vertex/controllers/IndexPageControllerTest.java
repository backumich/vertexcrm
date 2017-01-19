package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class IndexPageControllerTest {

    // todo : inspect and add/remove tests according to implemented code refactoring

    @Mock
    private Storage storage;

    private IndexPageController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexPageController(storage);
    }

    @Test
    public void showIndexPageShouldReturnCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("index"))
                .build();
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    public void sessionIdShouldBeSet() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(storage.getSessionId()).thenReturn(null);
        when(storage.getCount()).thenReturn(3L);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getId()).thenReturn("testSessionId");

        controller.showIndexPage(request);
        verify(storage).setSessionId("testSessionId");
    }
}