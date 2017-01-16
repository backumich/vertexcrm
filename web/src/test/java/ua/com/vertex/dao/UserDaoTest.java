package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserDaoInf userDao;

    private static final int EXISTING_ID = 22;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String EXISTING_EMAIL = "22@test.com";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";

    @Test
    public void getUserReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUser(EXISTING_ID);
        assertNotNull(optional);
        assertEquals(EXISTING_ID, optional.get().getUserId());
    }

    @Test
    public void getUserReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUser(NOT_EXISTING_ID);
        assertNotNull(optional);
        assertEquals(new User(), optional.orElse(new User()));
    }

    @Test
    public void logInReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.logIn(EXISTING_EMAIL);
        assertNotNull(optional);
        assertEquals(EXISTING_EMAIL, optional.get().getEmail());
    }

    @Test
    public void logInReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.logIn(NOT_EXISTING_EMAIL);
        assertNotNull(optional);
        assertEquals(new User(), optional.orElse(new User()));
    }
}
