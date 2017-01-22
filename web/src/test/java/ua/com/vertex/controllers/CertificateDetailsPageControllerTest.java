package ua.com.vertex.controllers;

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
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Storage;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CertificateDetailsPageControllerTest {

    @Mock
    private CertDetailsPageLogic certLogic;

    @Mock
    private UserLogic userLogic;

    @Mock
    private Storage storage;

    @Mock
    private Model model;

    @Mock
    private BindingResult result;

    @Mock
    private Certificate certificate;

    private CertificateDetailsPageController controller;

    private static final int EXISTING_USER_ID = 22;
    private static final int EXISTING_CERT_ID = 222;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String PROCESS_CERTIFICATE_DETAILS = "processCertificateDetails";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateDetailsPageController(certLogic, userLogic, storage);
    }

    @Test
    public void showCertificateDetailsPageReturnsPageView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(CERTIFICATE_DETAILS))
                .build();
        mockMvc.perform(get("/" + CERTIFICATE_DETAILS))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    public void processCertificateDetailsReturnsPageView() throws Exception {
        Optional<Certificate> optionalC = Optional.of(new Certificate());
        Optional<User> optionalU = Optional.of(new User());

        when(result.hasErrors()).thenReturn(false);
        when(certificate.getCertificationId()).thenReturn(EXISTING_CERT_ID);
        when(certLogic.getCertificateDetails(EXISTING_CERT_ID)).thenReturn(optionalC);
        when(certificate.getUserId()).thenReturn(EXISTING_USER_ID);
        when(userLogic.getUserById(EXISTING_USER_ID)).thenReturn(optionalU);

        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(CERTIFICATE_DETAILS))
                .build();
        mockMvc.perform(get("/" + PROCESS_CERTIFICATE_DETAILS))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    public void processCertificateDetailsAddsErrorAttributeAfterRequestingInvalidId() {
        when(result.hasErrors()).thenReturn(true);

        controller.processCertificateDetails(certificate, result, model);
        verify(model, times(1)).addAttribute("error", "Entered value must be a positive integer!");
    }

    @Test
    public void processCertificateDetailsAddsCertificateAttributeAfterRetrievingNotEmptyOptional() {
        Certificate certificate = new Certificate.Builder()
                .setCertificationId(EXISTING_CERT_ID)
                .setUserId(EXISTING_USER_ID)
                .setCertificationDate(LocalDate.now())
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
        User user = new User.Builder()
                .setUserId(EXISTING_USER_ID)
                .getInstance();
        Optional<Certificate> optionalC = Optional.of(certificate);
        Optional<User> optionalU = Optional.of(user);

        when(result.hasErrors()).thenReturn(false);
        when(certLogic.getCertificateDetails(EXISTING_CERT_ID)).thenReturn(optionalC);
        when(userLogic.getUserById(EXISTING_USER_ID)).thenReturn(optionalU);

        controller.processCertificateDetails(certificate, result, model);
        verify(model, times(1)).addAttribute("certificate", certificate);
    }

    @Test
    public void processCertificateDetailsAddsUserAttributeAfterRetrievingNotEmptyOptional() {
        Certificate certificate = new Certificate.Builder()
                .setCertificationId(EXISTING_CERT_ID)
                .setUserId(EXISTING_USER_ID)
                .setCertificationDate(LocalDate.now())
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
        User user = new User.Builder()
                .setUserId(EXISTING_USER_ID)
                .getInstance();
        Optional<Certificate> optionalC = Optional.of(certificate);
        Optional<User> optionalU = Optional.of(user);

        when(result.hasErrors()).thenReturn(false);
        when(certLogic.getCertificateDetails(EXISTING_CERT_ID)).thenReturn(optionalC);
        when(userLogic.getUserById(EXISTING_USER_ID)).thenReturn(optionalU);

        controller.processCertificateDetails(certificate, result, model);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    public void processCertificateDetailsAddsErrorAttributeAfterRetrievingEmptyCertificateOptional() {
        Certificate certificate = new Certificate.Builder()
                .setCertificationId(NOT_EXISTING_ID)
                .getInstance();
        Optional<Certificate> optionalC = Optional.empty();

        when(result.hasErrors()).thenReturn(false);
        when(certLogic.getCertificateDetails(NOT_EXISTING_ID)).thenReturn(optionalC);

        controller.processCertificateDetails(certificate, result, model);
        verify(model, times(1)).addAttribute("error", "No certificate with this ID!");
    }
}
