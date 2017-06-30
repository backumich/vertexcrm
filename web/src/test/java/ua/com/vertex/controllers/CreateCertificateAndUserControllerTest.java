package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.CertificateWithUserForm;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.AdminController.*;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.ADD_CERTIFICATE_AND_USER_JSP;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.CERTIFICATE_WITH_USER_FORM;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@RunWith(MockitoJUnitRunner.class)
public class CreateCertificateAndUserControllerTest {


    private final String MSG_INVALID_DATA = "Have wrong objects in model";
    private final String MSG_INVALID_VIEW = "Have wrong viewName in ModelAndView";

    private CreateCertificateAndUserController underTest;
    private Model model;
    private Certificate certificate;
    private User user;
    private CertificateWithUserForm certificateWithUserForm;

    @Mock
    private CertificateLogic certificateLogic;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        underTest = new CreateCertificateAndUserController(certificateLogic);
        model = new ExtendedModelMap();
        certificate = new Certificate.Builder().setUserId(1).setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional").setLanguage("Java").getInstance();
        user = new User.Builder().setUserId(1).setEmail("test@mail.com").setFirstName("Test").setLastName("Test")
                .getInstance();
        certificateWithUserForm = new CertificateWithUserForm();
        certificateWithUserForm.setCertificate(certificate);
        certificateWithUserForm.setUser(user);
    }

    @Test
    public void addCertificateAndCreateUserReturnCorrectDataToModelAndView() throws Exception {
        ModelAndView result = underTest.addCertificateAndCreateUser();
        assertEquals(MSG_INVALID_VIEW, result.getViewName(), ADD_CERTIFICATE_AND_USER_JSP);
        assertEquals(MSG_INVALID_DATA, result.getModel().get(CERTIFICATE_WITH_USER_FORM)
                , new CertificateWithUserForm());
    }

    @Test
    public void checkCertificateAndUserCalledInCertificateLogic() throws Exception {
        underTest.checkCertificateAndUser(certificateWithUserForm, bindingResult, model);
        verify(certificateLogic).addCertificateAndCreateUser(certificateWithUserForm.getCertificate()
                , certificateWithUserForm.getUser());
    }

    @Test
    public void checkCertificateAndUserHasCorrectDataInModelAndReturnCorrectView() throws Exception {
        when(certificateLogic.addCertificateAndCreateUser(certificate, user)).thenReturn(333);

        String returnPage = underTest.checkCertificateAndUser(certificateWithUserForm, bindingResult, model);

        assertEquals(MSG_INVALID_VIEW, returnPage, ADMIN_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("Certificate added. Certificate id = " + "333"));
    }

    @Test
    public void checkCertificateAndUserHasCorrectDataInModelAndReturnCorrectViewWhenDataIntegrityViolationException()
            throws Exception {
        when(certificateLogic.addCertificateAndCreateUser(certificate
                , user)).thenThrow(new DataIntegrityViolationException("Test"));

        assertEquals(MSG_INVALID_VIEW, underTest.checkCertificateAndUser(certificateWithUserForm, bindingResult, model)
                , ADD_CERTIFICATE_AND_USER_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("A person with this e-mail already exists, try again."));
    }

    @Test
    public void checkCertificateAndUserHasCorrectDataInModelAndReturnCorrectViewWhenException() throws Exception {
        when(certificateLogic.addCertificateAndCreateUser(certificate
                , user)).thenThrow(new Exception("Test"));

        assertEquals(MSG_INVALID_VIEW, underTest.checkCertificateAndUser(certificateWithUserForm, bindingResult, model)
                , ERROR);
    }

    @Test
    public void checkCertificateAndUserHasCorrectDataInModelAndReturnCorrectViewWhenBindingResultHasError()
            throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        assertEquals(MSG_INVALID_VIEW, underTest.checkCertificateAndUser(certificateWithUserForm, bindingResult, model)
                , ADD_CERTIFICATE_AND_USER_JSP);
        assertTrue(MSG_INVALID_DATA, model.containsAttribute(MSG));
        assertTrue(MSG_INVALID_DATA, model.asMap().containsValue("The data have not been validated!!!"));
    }

}
