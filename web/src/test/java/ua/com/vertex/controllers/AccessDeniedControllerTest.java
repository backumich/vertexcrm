package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.utils.Storage;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AccessDeniedControllerTest {

    @Mock
    private Storage storage;

    private AccessDeniedController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new AccessDeniedController(storage);
    }

    @Test
    public void certificateDetailsWebMvcShouldReturnPageView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("403"))
                .build();
        mockMvc.perform(get("/403"))
                .andExpect(view().name("403"));
    }
}
