package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static ua.com.vertex.controllers.StudentDetailsController.STUDENT_DETAILS_JSP;
import static ua.com.vertex.controllers.StudentDetailsController.USER;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class StudentDetailsControllerTest {
    private final String MSG = "Maybe method was changed";

    @Mock
    private UserLogic userLogic;

    private User user;
    private MockMvc mockMvc;
    private StudentDetailsController underTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new StudentDetailsController(userLogic);
        mockMvc = standaloneSetup(underTest)
                .setSingleView(new InternalResourceView(STUDENT_DETAILS_JSP))
                .build();
        user = new User.Builder().setUserId(1).setEmail("test@test.com").setFirstName("testFirstName")
                .setLastName("testLastName").setIsActive(true).setRole(Role.ROLE_TEACHER).setDiscount(5).getInstance();
    }

    @Test
    public void studentDetailsReturnsCorrectView() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/studentDetails"))
                .andExpect(view().name(STUDENT_DETAILS_JSP))
                .andExpect(status().isOk());
    }

    @Test
    public void studentDetailsHasCorrectObjectInModel() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/studentDetails"))
                .andExpect(status().is(400))
                .andExpect(model().attributeExists(USER))
                .andExpect(model().attribute(USER, user));
    }

    @Test
    public void studentDetailsHasCorrectObjectInModel2() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.of(user));
        assertTrue(MSG, underTest.studentDetails(1).getModel().containsValue(user));
    }
}



