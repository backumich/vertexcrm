package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.UserController.CERTIFICATES;
import static ua.com.vertex.controllers.UserController.USER_JSP;


public class UserControllerTest {

    private UserController underTest;

    private Model model;

    @Mock
    private LogInfo logInfo;

    @Mock
    private CertificateLogic certificateLogic;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new UserController(logInfo, certificateLogic);
        model = new ExtendedModelMap();
    }

    @Test
    public void userHasCorrectDataInModel() throws Exception {
        ModelAndView result = underTest.user();
        assertEquals("Have wrong viewName in ModelAndView", result.getViewName(), USER_JSP);
    }

    @Test
    public void getAllCertificateByUserEmailIsCalledOnUserLogic() throws Exception {
        underTest.getAllCertificatesByUserEmail(model);
        verify(certificateLogic).getAllCertificatesByUserEmail(anyString());
    }

    @Test
    public void getAllCertificateByUserEmailShouldReturnAppropriateString() {
        assertEquals("Return wrong view", "user", underTest.getAllCertificatesByUserEmail(model));
    }

    @Test
    public void getAllCertificateByUserEmailShouldReturnAppropriateStringWhenException() {
        //noinspection unchecked
        when(logInfo.getEmail()).thenThrow(Exception.class);
        assertEquals("Return wrong view", ERROR, underTest.getAllCertificatesByUserEmail(model));
    }

    @Test
    public void getAllCertificateByUserEmailShouldInsertCertificatesIntoModel() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder().getInstance());

        when(certificateLogic.getAllCertificatesByUserEmail("test")).thenReturn(certificates);
        when(logInfo.getEmail()).thenReturn("test");

        underTest.getAllCertificatesByUserEmail(model);

        Object actualResult = model.asMap().get(CERTIFICATES);

        assertNotNull(actualResult);
        assertEquals("Have wrong objects in model", certificates, actualResult);
    }
}