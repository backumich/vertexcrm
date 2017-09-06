package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Optional;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static ua.com.vertex.controllers.StudentDetailsController.STUDENT_DETAILS_JSP;
import static ua.com.vertex.controllers.StudentDetailsController.USER;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class StudentDetailsControllerTest {

    @Mock
    private UserLogic userLogic;

    private User user;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        StudentDetailsController controller = new StudentDetailsController(userLogic);
        mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(STUDENT_DETAILS_JSP))
                .build();
        user = new User.Builder().setFirstName("firstName").setLastName("lastName")
                .setUserId(1).setEmail("email@test.com").getInstance();
    }

    @Test
    public void studentDetailsReturnsCorrectView() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/studentDetails"))
                .andExpect(view().name(STUDENT_DETAILS_JSP))
                .andExpect(status().isOk());
    }

    @Test
    public void studentDetailsHasCorrectObjectInModel() throws Exception{
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/studentDetails"))
                .andExpect(status().is(400))
        .andExpect(model().attributeExists(USER))
        .andExpect(model().attribute(USER,user));
    }
}
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.StudentDetailsController.STUDENT_DETAILS_JSP;

@RunWith(MockitoJUnitRunner.class)
public class StudentDetailsControllerTest {
    private final String MSG = "Maybe method was changed";
    @Mock
    UserLogic userLogic;
    private StudentDetailsController underTest;
    private User user;

    @Before
    public void setUp() throws Exception {
        underTest = new StudentDetailsController(userLogic);
        user = new User.Builder().setUserId(1).setEmail("test@test.com").setFirstName("TestFirstName")
                .setLastName("TestLastName").setIsActive(true).setRole(Role.ROLE_TEACHER).setDiscount(5).getInstance();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void studentDetailsReturnCorrectViewWhenException() throws Exception {
        when(userLogic.getUserById(anyInt())).thenThrow(new DataIntegrityViolationException("Test"));
//        String r = underTest.studentDetails(1).getViewName();
//        System.out.println(r);
        assertEquals(MSG, underTest.studentDetails(1).getViewName(), ERROR);
    }

    @Test
    public void studentDetailsReturnCorrectView() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.of(user));
        assertEquals(MSG, underTest.studentDetails(1).getViewName(), STUDENT_DETAILS_JSP);
    }

    @Test
    public void studentDetailsHasCorrectOjectInModel() throws Exception {
        when(userLogic.getUserById(anyInt())).thenReturn(Optional.of(user));
        assertTrue(MSG, underTest.studentDetails(1).getModel().containsValue(user));
    }
}
