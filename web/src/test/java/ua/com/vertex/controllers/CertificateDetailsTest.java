package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.vertex.controllers.AdminController.CERTIFICATE;


public class CertificateDetailsTest {

    private final String MSG_INVALID_DATA = "Have wrong objects in model";

    private CertificateDetails underTest;

    private Certificate certificate;

    @Mock
    private CertificateLogic certificateLogic;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.underTest = new CertificateDetails(certificateLogic);
        certificate = new Certificate.Builder().setCertificationId(1)
                .setUserId(1).setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional").setLanguage("Java").getInstance();
    }

    @Test
    public void getCertificateByIdIsCalledOnCertificateLogic() throws Exception {
        when(certificateLogic.getCertificateById(1)).thenReturn(Optional.empty());
        underTest.getCertificateDetails(1);
        verify(certificateLogic).getCertificateById(1);
    }

    @Test
    public void getCertificateByIdReturnCorectView() throws Exception {
        when(certificateLogic.getCertificateById(anyInt())).thenReturn(java.util.Optional.ofNullable(certificate));

        ModelAndView modelAndView = underTest.getCertificateDetails(1);

        assertNotNull(modelAndView);
        assertTrue(modelAndView.hasView());
        assertEquals("Have wrong viewName in ModelAndView", CERTIFICATE, modelAndView.getViewName());
    }

    @Test
    public void getCertificateByIdShouldInsertCertificateIntoModelAndView() throws Exception {
        when(certificateLogic.getCertificateById(anyInt())).thenReturn(java.util.Optional.ofNullable(certificate));

        ModelAndView modelAndView = underTest.getCertificateDetails(1);

        assertNotNull(modelAndView);

        Object actualResult = modelAndView.getModel().get(CERTIFICATE);

        assertNotNull(actualResult);
        assertEquals(MSG_INVALID_DATA, certificate, actualResult);
    }

    @Test
    public void getCertificateByIdShouldInserNull() throws Exception {
        when(certificateLogic.getCertificateById(-1)).thenReturn(Optional.empty());

        assertNull(MSG_INVALID_DATA, underTest.getCertificateDetails(-1).getModel().get(CERTIFICATE));
    }
}