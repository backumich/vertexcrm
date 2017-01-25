package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

//@WebAppConfiguration
public class ViewAllUsersControllerTest {

    @Mock
    private UserLogic logic;

    @Mock
    private ViewAllUsersController viewAllUsersController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewAllUsersController = new ViewAllUsersController(logic);
        this.mockMvc = MockMvcBuilders.standaloneSetup(viewAllUsersController).build();
    }

    @Test
    public void viewAllUsersControllerReturnedListUsersTest() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(logic.getListUsers()).thenReturn(users);

        ModelAndView mav = viewAllUsersController.viewAllUsers();
        assertEquals(users, mav.getModel().get("users"));
    }

    @Test
    public void viewAllUsersControllerReturnedViewTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(viewAllUsersController)
                .setSingleView(new InternalResourceView("viewAllUsers"))
                .build();
        mockMvc.perform(get("/viewAllUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAllUsers"));
    }
}
