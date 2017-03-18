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
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.utils.LogInfo;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertificateDetailsPageControllerTest {

    @Autowired
    private CertDetailsPageLogic certLogic;

    @Autowired
    private LogInfo logInfo;

    @Mock
    private Model model;

    private Certificate certificate;
    private User user;

    private CertificateDetailsPageController controller;

    private static final int EXISTING_USER_ID = 22;
    private static final int EXISTING_CERT_ID = 222;
    private static final String EXISTING_CERT_ID_ENC = "4fa70a1d04f2746b08248afa69df19e4";
    private static final String NOT_EXISTING_CERT_ID_ENC = "00274718ec2443c64fa8b733f17bd1a9";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String CERTIFICATE_LINK = "certificateLink";
    private static final String GET_CERTIFICATE = "getCertificate";
    private static final String ERROR = "error";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateDetailsPageController(certLogic, logInfo);
        certificate = new Certificate.Builder()
                .setCertificationId(EXISTING_CERT_ID)
                .setUserId(EXISTING_USER_ID)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
        user = new User.Builder()
                .setUserId(EXISTING_USER_ID)
                .setEmail("22@test.com")
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setDiscount(0)
                .setPhoto(new byte[]{100})
                .setPassportScan(new byte[]{100})
                .setPhone("38066 000 00 00")
                .setRole(Role.USER)
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
    @WithAnonymousUser
    public void getCertificateReturnsPageView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(CERTIFICATE_DETAILS))
                .build();
        mockMvc.perform(get("/" + GET_CERTIFICATE + "/" + EXISTING_CERT_ID_ENC)
                .param("certificateIdEncoded", EXISTING_CERT_ID_ENC))
                .andExpect(view().name(CERTIFICATE_DETAILS));
    }

    @Test
    @WithAnonymousUser
    public void getCertificateAddsErrorAttributeAfterRequestingInvalidId() {
        String view = controller.getCertificate("invalid data", model);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAttribute(ERROR, "Invalid entered data");
    }

    @Test
    @WithAnonymousUser
    public void getCertificateAddsCertificateAttributesAfterRetrievingNotEmptyOptional() {
        String view = controller.getCertificate(EXISTING_CERT_ID_ENC, model);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAttribute(CERTIFICATE_LINK, EXISTING_CERT_ID_ENC);
        verify(model, times(1)).addAttribute("certificate", certificate);
    }

    @Test
    @WithAnonymousUser
    public void getCertificatesAddsUserAttributeAfterRetrievingNotEmptyOptional() {
        String view = controller.getCertificate(EXISTING_CERT_ID_ENC, model);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAttribute(CERTIFICATE_LINK, EXISTING_CERT_ID_ENC);
        verify(model, times(1)).addAttribute("certificate", certificate);
        verify(model, times(1)).addAttribute("user", user);

    }

    @Test
    @WithAnonymousUser
    public void getCertificateAddsErrorAttributeAfterRetrievingEmptyCertificateOptional() {
        String view = controller.getCertificate(NOT_EXISTING_CERT_ID_ENC, model);
        assertEquals(CERTIFICATE_DETAILS, view);
        verify(model, times(1)).addAttribute("error", "No certificate with this ID!");
    }
}
