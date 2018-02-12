package ua.com.vertex.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.context.TestConfig;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class EmailExtractorTest {
    private EmailExtractor emailExtractor;

    @Before
    public void setUp() {
        emailExtractor = new EmailExtractorImpl();
    }

    @Test
    @WithAnonymousUser
    public void nullIsReturnedForAnonymousUser() {
        String email = emailExtractor.getEmailFromAuthentication();
        assertTrue(email == null);
    }

    @Test
    @WithMockUser
    public void emailIsReturnedForLoggedInUser() {
        String email = emailExtractor.getEmailFromAuthentication();
        assertTrue(email != null);
    }
}