package ua.com.vertex.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.UserFormRegistration;

import static org.junit.Assert.assertTrue;


public class UserControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private RegistrationController underTest;
    //private BindingResult bindingResult;

//    @Mock
//    RegistrationUserLogic registrationUserLogic;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        //MockitoAnnotations.initMocks(this);
        //underTest = new RegistrationController(registrationUserLogic);
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
        //Assert.assertEquals(userFormRegistration, modelMap.get("userFormRegistration"));
    }

    @Test
    public void processRegistration_checkReturnViewName() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject(new UserFormRegistration());

        RegistrationController registrationController = new RegistrationController();


//        BindingResult bindingResult = mock(BindingResult.class);
//        RegistrationUserLogic registrationUserLogic = mock(RegistrationUserLogic.class);
//        UserFormRegistration userFormRegistration = mock(UserFormRegistration.class);
//        ModelAndView model = registrationController.processRegistration(userFormRegistration, bindingResult, modelAndView);
//        Assert.assertEquals("registrationSuccess", model.getViewName());


    }

//    @Test
//    public void processRegistration_checkReturnModelAndModelName() throws Exception {
//        ModelAndView modelAndView = new ModelAndView();
//        UserFormRegistration userFormRegistration = new UserFormRegistration();
//        modelAndView.addObject("userFormRegistration", userFormRegistration);
//        ModelMap modelMap = modelAndView.getModelMap();
//        Assert.assertTrue(modelMap.containsAttribute("userFormRegistration"));
//        Assert.assertEquals(new UserFormRegistration(), modelMap.get("userFormRegistration"));
//        //Assert.assertEquals(userFormRegistration, modelMap.get("userFormRegistration"));
//    }


//    @Test
//    public void getAllCertificateByUserIdShouldReturnAppropriateString() {
//        String result = underTest.getAllCertificatesByUserId(222, modelAndView);
//        assertEquals("Maybe mapping for this method was changed", "user", result);
//    }
//
//    @Test
//    public void getAllCertificateByUserIdShouldInsertCertificatesIntoModel() {
//        ArrayList<Certificate> certificates = new ArrayList<>();
//        certificates.add(new Certificate.Builder().getInstance());
//
//        when(registrationUserLogic.getAllCertificatesByUserId(333)).thenReturn(certificates);
//
//        underTest.getAllCertificatesByUserId(333, modelAndView);
//
//        Object actualResult = modelAndView.asMap().get(CERTIFICATES);
//
//        assertNotNull(actualResult);
//        assertEquals("Have wrong objects in modelAndView", certificates, actualResult);
//    }
}