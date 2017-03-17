package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.com.vertex.beans.Role.USER;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class LoggingLogicImplTest {

    @Mock
    private LogInfo logInfo;

    @Mock
    private LoggingLogic loggingLogic;

    @Mock
    private UserLogic userLogic;

    @Autowired
    private UserDaoInf userDao;

    private static final String EXISTING_EMAIL = "33@test.com";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";
    private static final String PASSWORD = "password";


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loggingLogic = new LoggingLogicImpl(logInfo, userLogic, userDao);
    }

    @Test
    public void logInWithEmptyEmailReturnsEmptyUser() {
        Optional<User> optional = loggingLogic.logIn("");
        assertTrue(!optional.isPresent());
    }

    @Test
    public void logInWithNotExistingEmailReturnsEmptyUser() {
        Optional<User> optional = loggingLogic.logIn(NOT_EXISTING_EMAIL);
        assertTrue(!optional.isPresent());
    }

    @Test
    public void logInWithExistingEmailReturnsExistingUser() {
        Optional<User> optional = userDao.logIn(EXISTING_EMAIL);
        User user = new User.Builder()
                .setEmail(EXISTING_EMAIL)
                .setPassword(PASSWORD)
                .setRole(USER)
                .getInstance();

        assertEquals(user, optional.get());
    }
}