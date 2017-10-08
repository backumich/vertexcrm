package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import ua.com.vertex.context.TestConfig;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LoggingLogicImplTest {
    private static final String USERNAME_USER = "22@test.com";
    private static final String USERNAME_ADMIN = "email1";
    private static final String USER_PAGE = "userProfile";
    private static final String ADMIN_PAGE = "admin";

    @Autowired
    private LoggingLogicImpl loggingLogic;

    private Model model;

    @Before
    public void setUp() {
        model = Mockito.mock(Model.class);
    }

    @Test
    @WithAnonymousUser
    public void logInForEmptyUsernameReturnsEmptyOptional() {
        assertEquals(Optional.empty(), loggingLogic.logIn(""));
    }

    @Test
    @WithAnonymousUser
    public void logInForNotEmptyUsernameReturnsNotEmptyOptional() {
        assertTrue(loggingLogic.logIn(USERNAME_USER).isPresent());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void setUserReturnsUserPage() {
        assertEquals(USER_PAGE, loggingLogic.setUser(USERNAME_USER, model));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void setUserReturnsAdminPage() {
        assertEquals(ADMIN_PAGE, loggingLogic.setUser(USERNAME_ADMIN, model));
    }

    @Test(expected = RuntimeException.class)
    @WithMockUser(roles = "USER")
    public void setUserForNotExistingUsernameThrowsException() {
        loggingLogic.setUser("", model);
    }
}
