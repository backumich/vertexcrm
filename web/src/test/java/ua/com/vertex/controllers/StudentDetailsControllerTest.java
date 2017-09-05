package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.Optional;

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
