package ua.com.vertex.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.UserDaoRealization;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("Duplicates")
@ContextConfiguration(classes = MainTestContext.class)
@RunWith(MockitoJUnitRunner.class)
public class RegistrationUserLogicImplTest {

    @Mock
    private UserFormRegistration userFormRegistration;

    @InjectMocks
    private RegistrationUserLogicImpl registrationUserLogic;

    @Mock
    private UserDaoRealization userDaoRealization;

    @Test
    public void isMatchPassword_True() {
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        userFormRegistration.setPassword("1");
        userFormRegistration.setVerifyPassword("1");
        Boolean result = registrationUserLogic.isMatchPassword(userFormRegistration);
        assertTrue("Result is (" + result + ")", result);
    }

    @Test
    public void isMatchPassword_False() {
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        userFormRegistration.setPassword("1");
        userFormRegistration.setVerifyPassword("2");
        Boolean result = registrationUserLogic.isMatchPassword(userFormRegistration);
        assertTrue("Result is (" + result + ")", !result);
    }

    @Test
    public void convertUserFormRegistrationToUser_True() {
        UserFormRegistration userFormRegistration = new UserFormRegistration();

        userFormRegistration.setEmail("email");
        userFormRegistration.setPassword("password");
        userFormRegistration.setVerifyPassword("verify password");
        userFormRegistration.setFirstName("first name");
        userFormRegistration.setLastName("last name");
        userFormRegistration.setPhone("phone");

        User user = registrationUserLogic.userFormRegistrationToUser(userFormRegistration);

        assertEquals("email", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("first name", user.getFirstName());
        assertEquals("last name", user.getLastName());
        assertEquals("phone", user.getPhone());

    }

    @Test
    public void convertUserFormRegistrationToUser_False() {
        UserFormRegistration userFormRegistration = new UserFormRegistration();

        userFormRegistration.setEmail("email");
        userFormRegistration.setPassword("password");
        userFormRegistration.setVerifyPassword("verify password");
        userFormRegistration.setFirstName("first name");
        userFormRegistration.setLastName("last name");
        userFormRegistration.setPhone("phone");

        User user = registrationUserLogic.userFormRegistrationToUser(userFormRegistration);

        assertNotEquals("", user.getEmail());
        assertNotEquals("", user.getPassword());
        assertNotEquals("", user.getFirstName());
        assertNotEquals("", user.getLastName());
        assertNotEquals("", user.getPhone());
    }

    @Test
    public void checkEmailAlreadyExists_True() {
        when(userFormRegistration.getEmail()).thenReturn("chewed.mole@gmail.com");
        when(userDaoRealization.isRegisteredEmail(userFormRegistration.getEmail())).thenReturn(true);

        userDaoRealization.isRegisteredEmail("chewed.mole@gmail.com");

        verify(userFormRegistration).getEmail();
        verify(userDaoRealization).isRegisteredEmail("chewed.mole@gmail.com");
    }
}
