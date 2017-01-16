package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.UserDaoImpl;

import static org.junit.Assert.assertEquals;
import static ua.com.vertex.beans.User.EMPTY_USER;
import static ua.com.vertex.utils.Role.USER;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserLogicImplTest {

    @Autowired
    private UserDaoImpl dao;

    private UserLogicImpl logic;

    private static final String EXISTING_EMAIL = "33@test.com";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";
    private static final String PASSWORD = "password";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logic = new UserLogicImpl(dao);
    }

    @Test
    public void logInWithEmptyEmailReturnsEmptyUser() {
        assertEquals(EMPTY_USER, logic.logIn(""));
    }

    @Test
    public void logInWithNotExistingEmailReturnsEmptyUser() {
        assertEquals(EMPTY_USER, logic.logIn(NOT_EXISTING_EMAIL));
    }

    @Test
    public void logInWithExistingEmailReturnsExistingUser() {
        User user = new User.Builder()
                .setEmail(EXISTING_EMAIL)
                .setPassword(PASSWORD)
                .setRole(USER)
                .getInstance();

        assertEquals(user, logic.logIn(EXISTING_EMAIL));
    }
}
