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
import ua.com.vertex.controllers.exceptionHandling.GlobalExceptionHandler;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.StudentDetailsController.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class StudentDetailsControllerTest {

    @Mock
    private UserLogic userLogic;

    private User user;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        StudentDetailsController underTest = new StudentDetailsController(userLogic);
        mockMvc = standaloneSetup(underTest)
                .setSingleView(new InternalResourceView(STUDENT_DETAILS_JSP))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        user = new User.Builder().setUserId(1).setEmail("test@test.com").setFirstName("testFirstName")
                .setLastName("testLastName").setIsActive(true).setRole(Role.ROLE_TEACHER).setDiscount(5).getInstance();
    }

    @Test
    public void studentDetailsReturnsCorrectView() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/studentDetails").param(USER_ID, "1"))
                .andExpect(view().name(STUDENT_DETAILS_JSP))
                .andExpect(status().isOk());
    }

    @Test()
    public void studentDetailsReturnsCorrectViewWhenException() throws Exception {
        when(userLogic.getUserById(anyInt())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/studentDetails").param(USER_ID, "1"))
                .andExpect(view().name(ERROR))
                .andExpect(status().isOk());
    }

    @Test
    public void studentDetailsCorrectView() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/studentDetails").param(USER_ID, "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(USER))
                .andExpect(model().attribute(USER, user));
    }

}



