package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.MailService;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationUserLogicImplMockTest {

    private final String MSG = "Maybe method was changed";
    private final String EMAIL = "test@test.com";

    private RegistrationUserLogicImpl registrationUserLogic;
    private UserFormRegistration userFormRegistrationCorrect, userFormRegistrationIncorrect;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserLogic userLogic;

    @Mock
    private MailService mailService;

    @Mock
    private EmailLogic emailLogic;

    @Before
    public void setUp() {
        registrationUserLogic = new RegistrationUserLogicImpl(userLogic, mailService, emailLogic);
        userFormRegistrationCorrect = new UserFormRegistration();
        userFormRegistrationCorrect.setEmail(EMAIL);
        String PASSWORD = "111111";
        userFormRegistrationCorrect.setPassword(PASSWORD);
        userFormRegistrationCorrect.setVerifyPassword(PASSWORD);
        userFormRegistrationIncorrect = new UserFormRegistration();
        userFormRegistrationIncorrect.setEmail(EMAIL);
        userFormRegistrationIncorrect.setPassword(PASSWORD);
        userFormRegistrationIncorrect.setVerifyPassword(PASSWORD + "1");

    }

    @Test
    public void isEmailAlreadyExists() {
        assertTrue(MSG, registrationUserLogic.isEmailAlreadyExists(Optional.ofNullable(new User.Builder().setEmail(EMAIL)
                .setIsActive(true).getInstance())));
        assertFalse(MSG, registrationUserLogic.isEmailAlreadyExists(Optional.ofNullable(new User.Builder().setEmail(EMAIL)
                .setIsActive(false).getInstance())));
    }

    @Test
    public void isRegisteredUserEmailAlreadyExists() {
        when(userLogic.userForRegistrationCheck(EMAIL)).thenReturn(Optional.ofNullable(new User.Builder().setEmail(EMAIL)
                .setIsActive(true).getInstance()));
        assertFalse(MSG, registrationUserLogic.registerUser(userFormRegistrationIncorrect, bindingResult));
    }

    @Test
    public void isRegisteredUserEmailAlreadyExistsButNotActive() {
        when(userLogic.userForRegistrationCheck(EMAIL)).thenReturn(Optional.ofNullable(new User.Builder().setEmail(EMAIL)
                .setIsActive(false).getInstance()));
        assertTrue(MSG, registrationUserLogic.registerUser(userFormRegistrationCorrect, bindingResult));
        verify(userLogic).registrationUserUpdate(new User(userFormRegistrationCorrect));
    }

    @Test
    public void isRegisteredUserWhenEmailNotExists() {
        when(userLogic.userForRegistrationCheck(EMAIL)).thenReturn(Optional.empty());
        assertTrue(MSG, registrationUserLogic.registerUser(userFormRegistrationCorrect, bindingResult));
        verify(userLogic).registrationUserInsert(new User(userFormRegistrationCorrect));
    }
}