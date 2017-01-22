package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.utils.DeleteTempFiles;
import ua.com.vertex.utils.LogInfo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class IndexPageControllerTest {

    @Mock
    private LogInfo logInfo;

    @Mock
    private DeleteTempFiles cleaner;

    private IndexPageController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexPageController(logInfo, cleaner);
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
    public void showIndexPageInvokesTempDirCleaner() {
        when(logInfo.getId()).thenReturn("testSessionId");

        controller.showIndexPage();
        verify(cleaner, times(1)).cleanTempDir();
    }
}
