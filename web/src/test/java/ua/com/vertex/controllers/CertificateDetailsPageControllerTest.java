package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.controllers.exceptionHandling.GlobalExceptionHandler;
import ua.com.vertex.controllers.exceptionHandling.NoCertificateException;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.LogInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertificateDetailsPageControllerTest {

    @Autowired
    private CertificateLogic certLogic;

    @Mock
    private Model model;

    @Mock
    private LogInfo logInfo;

    private Certificate certificate;
    private User user;
    private Map<String, Object> attributes;
    private MockMvc mockMvc;

    private CertificateDetailsPageController controller;

    private static final int EXISTING_USER_ID = 22;
    private static final int EXISTING_CERT_ID = 222;
    private static final String EXISTING_CERT_UID = "1492779828793888";
    private static final String EXISTING_CERT_UID_DASHES = "1492-7798-2879-3888";
    private static final String CERTIFICATE = "certificate";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String USER = "user";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateDetailsPageController(certLogic);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler(logInfo))
                .build();
        certificate = new Certificate.Builder()
                .setCertificationId(EXISTING_CERT_ID)
                .setUserId(EXISTING_USER_ID)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .setCertificateUid(EXISTING_CERT_UID)
                .getInstance();

        user = new User.Builder()
                .setUserId(EXISTING_USER_ID)
                .setEmail("22@test.com")
                .setPassword("password")
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setDiscount(0)
                .setPhoto(new byte[]{100})
                .setPassportScan(new byte[]{100})
                .setPhone("38066 000 00 00")
                .setRole(Role.ROLE_USER)
                .getInstance();

        attributes = new HashMap<>();
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
    @WithAnonymousUser
    public void getCertificateReturnsPageView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(CERTIFICATE_DETAILS))
                .build();
        mockMvc.perform(get("/getCertificate").param("certificateUid", EXISTING_CERT_UID))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    @WithAnonymousUser
    public void getCertificateByCertificateUidReturnsPageView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(CERTIFICATE_DETAILS))
                .build();
        mockMvc.perform(get("/getCertificate/" + EXISTING_CERT_UID))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test(expected = NoCertificateException.class)
    @WithAnonymousUser
    public void getCertificateAddsErrorAttributeAfterRequestingInvalidUid() {
        controller.getCertificate("invalid data", model);
    }

    @Test(expected = NoCertificateException.class)
    @WithAnonymousUser
    public void getCertificateByCertificateUidAddsErrorAttributeAfterRequestingInvalidUid() {
        controller.getCertificateByCertificateUid("invalid data", model);
    }

    @Test
    @WithAnonymousUser
    public void globalExceptionHandlerCatchesNoCertificateExceptionAfterEmptyUid() throws Exception {
        mockMvc.perform(get("/getCertificate").param("certificateUid", ""))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    @WithAnonymousUser
    public void globalExceptionHandlerCatchesNoCertificateExceptionAfterIncorrectUid() throws Exception {
        mockMvc.perform(get("/getCertificate").param("certificateUid", "incorrectUid"))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    @WithAnonymousUser
    public void globalExceptionHandlerCatchesNoCertificateExceptionAfterIncorrectUid1() throws Exception {
        mockMvc.perform(get("/getCertificate").param("certificateUid", EXISTING_CERT_UID))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    @WithAnonymousUser
    public void getCertificateAddsCertificateAttributesAfterRequestingValidUid() {
        String view = controller.getCertificate(EXISTING_CERT_UID, model);
        attributes.put(CERTIFICATE, certificate);
        attributes.put(USER, user);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAllAttributes(attributes);
    }

    @Test
    @WithAnonymousUser
    public void getCertificateByCertificateUidAddsCertificateAttributesAfterRequestingValidUid() {
        String view = controller.getCertificateByCertificateUid(EXISTING_CERT_UID, model);
        attributes.put(CERTIFICATE, certificate);
        attributes.put(USER, user);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAllAttributes(attributes);
    }

    @Test
    @WithAnonymousUser
    public void getCertificateAddsCertificateAttributesAfterRequestingValidUidWithDashes() {
        String view = controller.getCertificate(EXISTING_CERT_UID_DASHES, model);
        attributes.put(CERTIFICATE, certificate);
        attributes.put(USER, user);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAllAttributes(attributes);
    }

    @Test
    @WithAnonymousUser
    public void getCertificateByCertificateUidAddsCertificateAttributesAfterRequestingValidUidWithDashes() {
        String view = controller.getCertificateByCertificateUid(EXISTING_CERT_UID_DASHES, model);
        attributes.put(CERTIFICATE, certificate);
        attributes.put(USER, user);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAllAttributes(attributes);
    }
}
