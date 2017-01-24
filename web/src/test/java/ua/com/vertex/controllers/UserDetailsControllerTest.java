package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.logic.interfaces.UserLogic;

@WebAppConfiguration
public class UserDetailsControllerTest {

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
    public void userDetailsControllerReturnedUsersTest() throws Exception {
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        when(logic.getListUsers()).thenReturn(users);
//
//        ModelAndView mav = viewAllUsersController.viewAllUsers();
//        assertEquals(users, mav.getModel().get("users"));
    }

}
