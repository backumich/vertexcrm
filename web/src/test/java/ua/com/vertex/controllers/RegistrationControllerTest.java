package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.RegistrationUserLogicImpl;
import ua.com.vertex.utils.ReCaptchaService;

import javax.servlet.http.HttpServletRequest;

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
    private ReCaptchaService reCaptchaService;

    @Mock
    private HttpServletRequest httpServletRequest;

    private RegistrationController registrationController;
    private UserFormRegistration userFormRegistration;
    private final String NAME = "test";

    @Before
    public void setUp() {
        registrationController = new RegistrationController(registrationUserLogic, reCaptchaService);
        userFormRegistration = new UserFormRegistration();
        userFormRegistration.setEmail(NAME);
        userFormRegistration.setPassword(NAME);
        userFormRegistration.setVerifyPassword(NAME);
        userFormRegistration.setFirstName(NAME);
        userFormRegistration.setLastName(NAME);
    }

    @Test
    public void viewRegistrationFormReturnCorrectViewNameAndObjectInModel() {
        ModelAndView modelAndView = registrationController.viewRegistrationForm();
        assertEquals(MSG, RegistrationController.REGISTRATION_PAGE, modelAndView.getViewName());
        assertEquals(MSG, new UserFormRegistration(), modelAndView.getModel().get(RegistrationController.NAME_MODEL));
    }

    @Test
    public void processRegistrationReturnCorrectViewWhenBindingResultHasError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        ModelAndView modelAndView = registrationController.processRegistration(userFormRegistration, bindingResult,
                new ModelAndView(), httpServletRequest);
        assertEquals(MSG, RegistrationController.REGISTRATION_PAGE, modelAndView.getViewName());
    }

    @Test
    public void processRegistrationReturnCorrectView() {
        String reCaptchaResponse = httpServletRequest.getParameter("g-recaptcha-response");
        String reCaptchaRemoteAddr = httpServletRequest.getRemoteAddr();
        when(reCaptchaService.verify(reCaptchaResponse, reCaptchaRemoteAddr)).thenReturn(true);
        when(registrationUserLogic.registerUser(userFormRegistration, bindingResult)).thenReturn(true);
        ModelAndView modelAndView = registrationController.processRegistration(userFormRegistration, bindingResult,
                new ModelAndView(), httpServletRequest);
        assertEquals(MSG, RegistrationController.REGISTRATION_SUCCESS_PAGE, modelAndView.getViewName());
    }
}