package ua.com.vertex.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.logic.interfaces.RegistrationUserLogic;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@SuppressWarnings("Duplicates")
public class RegistrationControllerTest {

    @Mock
    RegistrationUserLogic registrationUserLogic;

    @Mock
    private ModelAndView modelAndView;

    @Mock
    private BindingResult bindingResult;

    private RegistrationController registrationController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        registrationController = new RegistrationController(registrationUserLogic);
    }

    @Test
    public void viewRegistrationForm_checkReturnViewName() throws Exception {
        RegistrationController registrationController = new RegistrationController();
        ModelAndView modelAndView = registrationController.viewRegistrationForm();
        Assert.assertEquals("registration", modelAndView.getViewName());
    }

    @Test
    public void viewRegistrationForm_checkReturnModelAndModelName() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        modelAndView.addObject("userFormRegistration", userFormRegistration);
        ModelMap modelMap = modelAndView.getModelMap();
        assertTrue(modelMap.containsAttribute("userFormRegistration"));
        Assert.assertEquals(new UserFormRegistration(), modelMap.get("userFormRegistration"));
    }

    @Test
    public void viewRegistrationForm_checkReturnTypeObjects() throws Exception {
        UserFormRegistration userFormRegistration = mock(UserFormRegistration.class);
        registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);

        Assert.assertTrue(userFormRegistration instanceof UserFormRegistration);
        Assert.assertTrue(bindingResult instanceof BindingResult);
        Assert.assertTrue(modelAndView instanceof ModelAndView);
    }

    //------------------------
    @Test
    public void processRegistration_checkReturnViewName() throws Exception {
        UserFormRegistration userFormRegistration = mock(UserFormRegistration.class);
        ModelAndView outModelAndView = registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
        Assert.assertEquals("registrationSuccess", outModelAndView.getViewName());
    }

    @Test
    public void processRegistration_checkReturnModelAndModelName() throws Exception {
        //UserFormRegistration userFormRegistration = mock(UserFormRegistration.class);
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        //ModelAndView modelAndView = new ModelAndView();

        ModelAndView outModelAndView;
        modelAndView.addObject("userFormRegistration", userFormRegistration);

        outModelAndView = registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
        //outModelAndView = registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
        ModelMap modelMap = outModelAndView.getModelMap();
        assertTrue(modelMap.containsAttribute("userFormRegistration"));
        //Assert.assertEquals(userFormRegistration, modelMap.get("userFormRegistration"));
    }

    @Test
    public void processRegistration_checkReturnTypeObjects() throws Exception {
        UserFormRegistration userFormRegistration = mock(UserFormRegistration.class);
        registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);

        Assert.assertTrue(userFormRegistration instanceof UserFormRegistration);
        Assert.assertTrue(bindingResult instanceof BindingResult);
        Assert.assertTrue(modelAndView instanceof ModelAndView);
    }
}