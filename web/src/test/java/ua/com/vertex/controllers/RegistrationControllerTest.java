package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.RegistrationUserLogicImpl;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.utils.MailService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    private final String MSG = "Maybe method was changed";

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RegistrationUserLogicImpl registrationUserLogic;

    @Mock
    private EmailLogic emailLogic;

    @Mock
    private MailService mailService;

    private RegistrationController registrationController;
    private UserFormRegistration userFormRegistration;
    private final String NAME = "test";


    @Before
    public void setUp() throws Exception {
        registrationController = new RegistrationController(registrationUserLogic, emailLogic, mailService);
        userFormRegistration = new UserFormRegistration();
        userFormRegistration.setEmail(NAME);
        userFormRegistration.setPassword(NAME);
        userFormRegistration.setVerifyPassword(NAME);
        userFormRegistration.setFirstName(NAME);
        userFormRegistration.setLastName(NAME);
    }

    @Test
    public void viewRegistrationFormReturnCorrectViewNameAndObjectInModel() throws Exception {
        ModelAndView modelAndView = registrationController.viewRegistrationForm();
        assertEquals(MSG, RegistrationController.REGISTRATION_PAGE, modelAndView.getViewName());
        assertEquals(MSG, new UserFormRegistration(), modelAndView.getModel().get(RegistrationController.NAME_MODEL));
    }

    @Test
    public void processRegistrationReturnCorrectViewWhenBindingResultHasError() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        ModelAndView modelAndView = registrationController.processRegistration(userFormRegistration, bindingResult,
                new ModelAndView());
        assertEquals(MSG, RegistrationController.REGISTRATION_PAGE, modelAndView.getViewName());
    }

    @Test
    public void processRegistrationReturnCorrectViewWhenDataAccessException() throws Exception {
        when(registrationUserLogic.isRegisteredUser(userFormRegistration, bindingResult)).thenThrow(new DataIntegrityViolationException(NAME));
        ModelAndView modelAndView = registrationController.processRegistration(userFormRegistration, bindingResult,
                new ModelAndView());
        assertEquals(MSG, RegistrationController.REGISTRATION_ERROR_PAGE, modelAndView.getViewName());
    }

    @Test
    public void processRegistrationReturnCorrectViewWhenException() throws Exception {
        when(registrationUserLogic.isRegisteredUser(userFormRegistration, bindingResult)).thenThrow(new Exception(NAME));
        ModelAndView modelAndView = registrationController.processRegistration(userFormRegistration, bindingResult,
                new ModelAndView());
        assertEquals(MSG, CertificateDetailsPageController.ERROR, modelAndView.getViewName());
    }

    @Test
    public void processRegistrationReturnCorrectView() throws Exception {
        when(registrationUserLogic.isRegisteredUser(userFormRegistration, bindingResult)).thenReturn(true);
        ModelAndView modelAndView = registrationController.processRegistration(userFormRegistration, bindingResult,
                new ModelAndView());
        assertEquals(MSG, RegistrationController.REGISTRATION_SUCCESS_PAGE, modelAndView.getViewName());
    }

}