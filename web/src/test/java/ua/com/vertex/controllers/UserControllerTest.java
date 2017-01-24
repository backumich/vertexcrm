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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.UserController.CERTIFICATES;
import static ua.com.vertex.controllers.UserController.USER_JSP;


public class UserControllerTest {

    private UserController underTest;

    private Model model;

    @Mock
    private CertificateLogic certificateLogic;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new UserController(certificateLogic);
        model = new ExtendedModelMap();
    }

    @Test
    public void userHasCorrectDataInModel() throws Exception {
        ModelAndView result = underTest.user();
        assertEquals("Have wrong viewName in ModelAndView", result.getViewName(), USER_JSP);
    }

    @Test
    public void getAllCertificateByUserIdIsCalledOnUserLogic() throws Exception {
        underTest.getAllCertificatesByUserId(111, model);
        verify(certificateLogic).getAllCertificatesByUserId(anyInt());
    }

    @Test
    public void getAllCertificateByUserIdShouldReturnAppropriateString() {
        String result = underTest.getAllCertificatesByUserId(222, model);
        assertEquals("Return wrong view", "user", result);
    }

    @Test
    public void getAllCertificateByUserIdShouldInsertCertificatesIntoModel() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder().getInstance());

        when(certificateLogic.getAllCertificatesByUserId(333)).thenReturn(certificates);

        underTest.getAllCertificatesByUserId(333, model);

        Object actualResult = model.asMap().get(CERTIFICATES);

        assertNotNull(actualResult);
        assertEquals("Have wrong objects in model", certificates, actualResult);
    }
}