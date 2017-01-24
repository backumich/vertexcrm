package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.logic.interfaces.UserLogic;

public class ViewAllUsersControllerTest {

    @Mock
    private UserLogic logic;

    @Mock
    private ViewAllUsersController viewAllUsersController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewAllUsersController = new ViewAllUsersController(logic);
    }

    @Test
    public void viewAllUsersControllerRedirectTest() throws Exception {
//        mockMvc = MockMvcBuilders
//                .annotationConfigSetup(JpaTestConfig.class, TestConfig.class)
//                .build();
//        viewAllUsersController.perform(get('/'))
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl('WEB-INF/pages/index.jsp'));
    }

}
