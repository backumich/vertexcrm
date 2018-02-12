package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.EmailExtractor;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.UserController.CERTIFICATES;
import static ua.com.vertex.controllers.UserController.USER_JSP;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private UserController underTest;

    private Model model;

    @Mock
    private CertificateLogic certificateLogic;
    @Mock
    private EmailExtractor emailExtractor;

    @Before
    public void setUp() throws Exception {
        underTest = new UserController(certificateLogic, emailExtractor);
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
    public void getAllCertificateByUserEmailShouldInsertCertificatesIntoModel() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder().getInstance());

        when(certificateLogic.getAllCertificatesByUserEmail("test")).thenReturn(certificates);
        when(emailExtractor.getEmailFromAuthentication()).thenReturn("test");

        underTest.getAllCertificatesByUserEmail(model);

        Object actualResult = model.asMap().get(CERTIFICATES);

        assertNotNull(actualResult);
        assertEquals("Have wrong objects in model", certificates, actualResult);
    }
}