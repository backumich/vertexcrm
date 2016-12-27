package ua.com.vertex.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.utils.Storage;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CertificateDetailsPageControllerTest {

    @Mock
    private CertDetailsPageLogic logic;

    @Mock
    private Storage storage;

    @Mock
    private Model model;

    @Mock
    private BindingResult result;

    @Mock
    private Certificate certificate;

    @Mock
    private User user;

    private CertificateDetailsPageController controller;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateDetailsPageController(logic, storage);
    }

    @Test
    public void webMvcShouldReturnCorrectView() throws Exception {
        when(logic.getCertificateDetails(222)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(22);
        when(logic.getUserDetails(22)).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("certificateDetails"))
                .build();
        mockMvc.perform(get("/processCertificateDetails"))
                .andExpect(view().name("certificateDetails"));
    }

    @Test
    public void FillingModelAttributesAfterRetrievingCertificateAndUser() {
        when(certificate.getCertificationId()).thenReturn(222);
        when(logic.getCertificateDetails(222)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(22);
        when(logic.getUserDetails(22)).thenReturn(user);
        when(user.getUserId()).thenReturn(22);

        controller.processCertificateDetails(certificate, result, model);
        verify(model).addAttribute("certificate", certificate);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void FillingErrorAttributeAfterRequestingInvalidId() {
        when(result.hasErrors()).thenReturn(true);

        controller.processCertificateDetails(certificate, result, model);
        verify(model).addAttribute("error", "Entered value must be > 0");
    }

    @Test
    public void FillingModelAttributesAfterRetrievingEmptyCertificate() {
        when(certificate.getCertificationId()).thenReturn(0);
        when(logic.getCertificateDetails(0)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(0);
        when(logic.getUserDetails(22)).thenReturn(user);

        controller.processCertificateDetails(certificate, result, model);
        verify(model).addAttribute("error", "No certificate with this ID! Try again!");
    }

    @Test
    public void FillingModelAttributesAfterRetrievingEmptyUser() {
        when(certificate.getCertificationId()).thenReturn(500);
        when(logic.getCertificateDetails(500)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(0);
        when(logic.getUserDetails(0)).thenReturn(user);
        when(user.getUserId()).thenReturn(0);

        controller.processCertificateDetails(certificate, result, model);
        verify(model).addAttribute("certificate", certificate);
    }


    @Test
    public void FillingModelAttributeAfterRetrievingPhoto() throws IOException {
        byte[] data = {(byte) 1};

        when(storage.getImageData()).thenReturn(data);
        String encodedImage = Base64.encode(data);

        controller.showUserPhoto(model);
        verify(model).addAttribute("image", encodedImage);
    }
}
