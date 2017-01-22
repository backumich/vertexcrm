package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.utils.DeleteTempFiles;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class IndexPageControllerTest {

    @Mock
    private Storage storage;

    @Mock
    private DeleteTempFiles cleaner;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private IndexPageController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexPageController(storage, cleaner);
    }

    @Test
    public void showIndexPageReturnsView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("index"))
                .build();
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    public void showIndexPageSetsSessionId() {
        when(storage.getSessionId()).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getId()).thenReturn("testSessionId");

        controller.showIndexPage(request);
        verify(storage, times(1)).setSessionId("testSessionId");
    }

    @Test
    public void showIndexPageInvokesTempDirCleaner() {
        when(storage.getSessionId()).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getId()).thenReturn("testSessionId");

        controller.showIndexPage(request);
        verify(cleaner, times(1)).cleanTempDir();
    }
}
