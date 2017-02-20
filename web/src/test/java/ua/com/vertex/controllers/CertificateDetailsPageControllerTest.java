package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

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
    private LogInfo logInfo;

    @Mock
    private Model model;

    private Certificate certificate;
    private User user;

    private CertificateDetailsPageController controller;

    private static final int EXISTING_USER_ID = 22;
    private static final int EXISTING_CERT_ID = 222;
    private static final String EXISTING_CERT_ID_ENC = "TWpJeWQyaGhkQ0JoSUhCaGFXND0=";
    private static final String NOT_EXISTING_CERT_ID_ENC = "TFRJeE5EYzBPRE0yTkRoM2FHRjBJR0VnY0dGcGJnPT0=";
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String GET_CERTIFICATE = "getCertificate";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateDetailsPageController(certLogic, userLogic, logInfo);
        certificate = new Certificate.Builder()
                .setCertificationId(EXISTING_CERT_ID)
                .setUserId(EXISTING_USER_ID)
                .setCertificationDate(LocalDate.now())
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
        user = new User.Builder()
                .setUserId(EXISTING_USER_ID)
                .getInstance();
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
    public void getCertificateReturnsPageView() throws Exception {
        Optional<Certificate> optionalC = Optional.of(certificate);
        Optional<User> optionalU = Optional.of(user);

        when(certLogic.getCertificateDetails(EXISTING_CERT_ID)).thenReturn(optionalC);
        when(userLogic.getUserById(EXISTING_USER_ID)).thenReturn(optionalU);

        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(CERTIFICATE_DETAILS))
                .build();
        mockMvc.perform(get("/" + GET_CERTIFICATE + "/" + EXISTING_CERT_ID_ENC)
                .param("certificateIdEncoded", EXISTING_CERT_ID_ENC))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    public void getCertificateAddsErrorAttributeAfterRequestingInvalidId() {
        controller.getCertificate("invalid data", model);
        verify(model, times(1)).addAttribute("error", "Invalid entered data");
    }

    @Test
    public void getCertificateAddsCertificateAttributeAfterRetrievingNotEmptyOptional() {
        Optional<Certificate> optionalC = Optional.of(certificate);
        Optional<User> optionalU = Optional.of(user);

        when(certLogic.getCertificateDetails(EXISTING_CERT_ID)).thenReturn(optionalC);
        when(userLogic.getUserById(EXISTING_USER_ID)).thenReturn(optionalU);
        when(logInfo.getId()).thenReturn("id");

        controller.getCertificate(EXISTING_CERT_ID_ENC, model);
        verify(model, times(1)).addAttribute("certificate", certificate);
    }

    @Test
    public void getCertificatesAddsUserAttributeAfterRetrievingNotEmptyOptional() {
        Optional<Certificate> optionalC = Optional.of(certificate);
        Optional<User> optionalU = Optional.of(user);

        when(certLogic.getCertificateDetails(EXISTING_CERT_ID)).thenReturn(optionalC);
        when(userLogic.getUserById(EXISTING_USER_ID)).thenReturn(optionalU);

        controller.getCertificate(EXISTING_CERT_ID_ENC, model);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    public void getCertificateAddsErrorAttributeAfterRetrievingEmptyCertificateOptional() {
        Optional<Certificate> optionalC = Optional.empty();

        when(certLogic.getCertificateDetails(NOT_EXISTING_ID)).thenReturn(optionalC);

        controller.getCertificate(NOT_EXISTING_CERT_ID_ENC, model);
        verify(model, times(1)).addAttribute("error", "No certificate with this ID!");
    }
}
