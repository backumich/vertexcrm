package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.UserLogic;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.UserController.CERTIFICATES;


public class UserControllerTest {

    UserController underTest;

    @Mock
    UserLogic userLogic;

    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new UserController(userLogic);
        model = new ExtendedModelMap();
    }

    @Test
    public void getAllCertificateByUserIdIsCalledOnUserLogic() throws Exception {
        underTest.getAllCertificateByUserId(111, model);
        verify(userLogic).getAllCertificateByUserId(anyInt());
    }

    @Test
    public void getAllCertificateByUserIdShouldReturnAppropriateString() {
        String result = underTest.getAllCertificateByUserId(222, model);
        assertEquals("Maybe mapping for this method was changed", "user.jsp", result);
    }

    @Test
    public void getAllCertificateByUserIdShouldInsertCertificatesIntoModel() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder().getInstance());

        when(userLogic.getAllCertificateByUserId(333)).thenReturn(certificates);

        underTest.getAllCertificateByUserId(333, model);

        Object actualResult = model.asMap().get(CERTIFICATES);

        assertNotNull(actualResult);
        assertEquals("Have wrong objects in model", certificates, actualResult);
    }
}