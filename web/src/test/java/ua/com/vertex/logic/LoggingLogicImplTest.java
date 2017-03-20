package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.LoggingLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoggingLogicImplTest {

    @Mock
    private LogInfo logInfo;

    @Mock
    private UserLogic userLogic;

    @Mock
    private UserDaoInf userDao;

    private LoggingLogic loggingLogic;

    private static final String EMAIL = "email@test.com";


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
    public void logInWithNotEmptyEmailInvokesDao() {
        loggingLogic.logIn(EMAIL);
        verify(userDao, times(1)).logIn(EMAIL);
    }
}
