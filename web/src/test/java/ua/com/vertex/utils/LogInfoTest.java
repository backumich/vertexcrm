package ua.com.vertex.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.context.TestConfig;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LogInfoTest {

    @Autowired
    private LogInfo logInfo;

    @Test
    @WithMockUser(username = "testUser@email.com")
    public void getUsername() {
        assertEquals(logInfo.getEmail(), "testUser@email.com");
    }

    @Test
    @WithMockUser
    public void getIdMockUSer() {
        assertTrue(logInfo.getId().contains("Session id"));
        assertTrue(logInfo.getId().contains("Email"));
    }

    @Test
    @WithAnonymousUser
    public void getIdAnonymousUser() {
        assertTrue(logInfo.getId().contains("Session id"));
        assertFalse(logInfo.getId().contains("Login"));
    }
}
