package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.AdminController.*;


public class AdminControllerTest {

    private AdminController underTest;

    private Model model;

    private Certificate certificate;

    @Mock
    private CertificateLogic certificateLogic;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new AdminController(certificateLogic);
        model = new ExtendedModelMap();
        certificate = new Certificate.Builder()
                .setUserId(1)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
    }

    @Test
    public void adminHasCorrectDataInModel() throws Exception {
        ModelAndView result = underTest.admin();
        assertEquals("Have wrong viewName in ModelAndView", result.getViewName(), ADMIN_JSP);
    }

    @Test
    public void addCertificateHasCorrectDataInModel() throws Exception {
        ModelAndView result = underTest.addCertificate();
        assertEquals("Have wrong viewName in ModelAndView", result.getViewName(), ADD_CERTIFICATE_JSP);
        assertTrue("Have wrong objects in model", result.getModel().containsValue(new Certificate()));
    }

    @Test
    public void checkCertificateIsCalledInCertificateLogic() throws Exception {
        underTest.checkCertificate(certificate, bindingResult, model);
        verify(certificateLogic).addCertificate(certificate);
    }

    @Test
    public void checkCertificateRedirectToCorrectPage() throws Exception {
        assertEquals("Return wrong view", underTest.checkCertificate(certificate, bindingResult, model)
                , ADD_CERTIFICATE_JSP);
    }

    @Test
    public void checkCertificateHasCorrectDataInModel() throws Exception {
        when(certificateLogic.addCertificate(certificate)).thenReturn(1);
        underTest.checkCertificate(certificate, bindingResult, model);
        assertTrue("Have wrong objects in model", model.containsAttribute(MSG));

        assertTrue("Have wrong message in model ", model.asMap().containsValue("Certificate added. Certificate id=1"));
    }

}