package ua.com.vertex.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.vertex.context.TestConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AnonymousUserAccessRightsTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private static final String CORRECT_PASSWORD = "123456";
    private static final String INCORRECT_PASSWORD = "1234567";
    private static final String EXISTING_EMAIL = "44@test.com";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testFormLoginWithCorrectPassword() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", EXISTING_EMAIL)
                .param("password", CORRECT_PASSWORD)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/loggedIn"));
    }

    @Test
    public void testFormLoginWithIncorrectPassword() throws Exception {
        mockMvc.perform(post("/logIn")
                .param("username", EXISTING_EMAIL)
                .param("password", INCORRECT_PASSWORD)
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logIn?error"));
    }

    @Test
    @WithMockUser
    public void testFormLogout() throws Exception {
        mockMvc.perform(post("/logOut")
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/loggedOut"));
    }

    @Test
    @WithAnonymousUser
    public void testCss() throws Exception {
        mockMvc.perform(get("/css/**"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testJS() throws Exception {
        mockMvc.perform(get("/javascript/**"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testRegistration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testLogIn() throws Exception {
        mockMvc.perform(get("/logIn"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testLoggedIn() throws Exception {
        mockMvc.perform(get("/loggedIn"))
                .andExpect(redirectedUrl("http://localhost/logIn"));
    }

    @Test
    @WithAnonymousUser
    public void testLogOut() throws Exception {
        mockMvc.perform(get("/logOut"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testLoggedOut() throws Exception {
        mockMvc.perform(get("/loggedOut"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testCertificateDetails() throws Exception {
        mockMvc.perform(get("/certificateDetails"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testProcessCertificateDetails() throws Exception {
        mockMvc.perform(post("/getCertificate"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testCertificateHolderPhoto() throws Exception {
        mockMvc.perform(get("/showImage"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void test403() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(redirectedUrl(null));
    }
}
