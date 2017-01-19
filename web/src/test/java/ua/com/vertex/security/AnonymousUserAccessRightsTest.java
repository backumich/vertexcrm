package ua.com.vertex.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.vertex.context.MainTestContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AnonymousUserAccessRightsTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
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
        mockMvc.perform(post("/processCertificateDetails"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    @WithAnonymousUser
    public void testCertificateHolderPhoto() throws Exception {
        mockMvc.perform(get("/userPhoto"))
                .andExpect(redirectedUrl(null));
    }
}
