package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.dao.UserDaoRealization;
import ua.com.vertex.logic.RegistrationUserLogicImpl;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.utils.MailService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SuppressWarnings("Duplicates")
public class RegistrationControllerTest {
    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDaoRealization userDao;

    @Spy
    @InjectMocks
    private RegistrationUserLogicImpl registrationUserLogic;

    @Mock
    private EmailLogic emailLogic;

    @Mock
    private MailService mailService;

    @Mock
    private RegistrationController registrationController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        registrationController = new RegistrationController(registrationUserLogic, emailLogic, mailService);
    }

    @Test
    public void viewRegistrationForm_checkReturnViewName() throws Exception {
        ModelAndView modelAndView = registrationController.viewRegistrationForm();
        assertEquals(RegistrationController.REGISTRATION_PAGE, modelAndView.getViewName());
    }

    @Test
    public void processRegistration_checkReturnViewName() throws Exception {
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        userFormRegistration.setPassword("testPassword");
        userFormRegistration.setVerifyPassword("testPassword");

        ModelAndView modelAndView = new ModelAndView();

        ModelAndView outModelAndView =
                registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
        assertEquals("registrationSuccess", outModelAndView.getViewName());
    }

    @Test
    public void processRegistration_checkReturnModelAndModelName() throws Exception {
        UserFormRegistration userFormRegistration = new UserFormRegistration();
        userFormRegistration.setPassword("testPassword");
        userFormRegistration.setVerifyPassword("testPassword");
        ModelAndView modelAndView = new ModelAndView();

        ModelAndView outModelAndView;
        modelAndView.addObject("userFormRegistration", userFormRegistration);

        outModelAndView = registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);

        ModelMap modelMap = outModelAndView.getModelMap();
        assertTrue(modelMap.containsAttribute("userFormRegistration"));
        assertEquals(userFormRegistration, modelMap.get("userFormRegistration"));
    }

    @Test
    public void registrationControllerReturnedViewTest() throws Exception {
        MockMvc mockMvc = standaloneSetup(registrationController)
                .setSingleView(new InternalResourceView("registration"))
                .build();
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}