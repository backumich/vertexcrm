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

@SuppressWarnings("Duplicates")
public class RegistrationControllerTest {

    @Mock
    RegistrationUserLogic registrationUserLogic;

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
        //todo: registrationController exists as a variable, use it please
        RegistrationController registrationController = new RegistrationController(registrationUserLogic);
        ModelAndView modelAndView = registrationController.viewRegistrationForm();
        //todo: RegistrationController.REGISTRATION_PAGE should be used
        Assert.assertEquals("registration", modelAndView.getViewName());
    }

    @Test
    public void viewRegistrationForm_checkReturnModelAndModelName() throws Exception {
        //todo: you are testing ModelAndMap in this method, it is not your job
        ModelAndView modelAndView = new ModelAndView();
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        modelAndView.addObject("userFormRegistration", userFormRegistration);
        ModelMap modelMap = modelAndView.getModelMap();
        assertTrue(modelMap.containsAttribute("userFormRegistration"));
        Assert.assertEquals(new UserFormRegistration(), modelMap.get("userFormRegistration"));
    }

    @Test
    public void processRegistration_checkReturnViewName() throws Exception {
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        ModelAndView modelAndView = new ModelAndView();
        ModelAndView outModelAndView =
                registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
        Assert.assertEquals("registrationSuccess", outModelAndView.getViewName());
    }

    @Test
    public void processRegistration_checkReturnModelAndModelName() throws Exception {

        UserFormRegistration userFormRegistration = new UserFormRegistration();
        ModelAndView modelAndView = new ModelAndView();

        ModelAndView outModelAndView;
        modelAndView.addObject("userFormRegistration", userFormRegistration);

        //????????????????????????????
        outModelAndView = registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
        //????????????????????????????

        ModelMap modelMap = outModelAndView.getModelMap();
        assertTrue(modelMap.containsAttribute("userFormRegistration"));
        //Assert.assertEquals(userFormRegistration, modelMap.get("userFormRegistration"));
    }
}