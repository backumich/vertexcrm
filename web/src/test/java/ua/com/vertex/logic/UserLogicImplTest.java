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

import java.util.Optional;

import static org.junit.Assert.*;
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
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";
    private static final int EXISTING_ID1 = 33;
    private static final int EXISTING_ID2 = 22;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String PASSWORD = "password";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logic = new UserLogicImpl(dao);
    }

    @Test
    public void logInWithEmptyEmailReturnsEmptyUser() {
        Optional<User> optional = logic.logIn("");
        assertEquals(EMPTY_USER, optional.get());
    }

    @Test
    public void logInWithNotExistingEmailReturnsEmptyUser() {
        Optional<User> optional = logic.logIn(NOT_EXISTING_EMAIL);
        assertEquals(EMPTY_USER, optional.get());
    }

    @Test
    public void logInWithExistingEmailReturnsExistingUser() {
        Optional<User> optional = logic.logIn(EXISTING_EMAIL);
        User user = new User.Builder()
                .setEmail(EXISTING_EMAIL)
                .setPassword(PASSWORD)
                .setRole(USER)
                .getInstance();

        assertEquals(user, optional.get());
    }

    @Test
    public void getUserByIdWithNotExistingIdReturnsNullOptional() {
        Optional<User> optional = logic.getUserById(NOT_EXISTING_ID);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    public void getUserByIdWithExistingIdReturnsExistingUser() {
        Optional<User> optional = logic.getUserById(EXISTING_ID1);
        User user = new User.Builder()
                .setUserId(EXISTING_ID1)
                .setEmail(EXISTING_EMAIL)
                .setPassword(PASSWORD)
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setDiscount(0)
                .setPhone("38066 000 00 00")
                .setRole(USER)
                .getInstance();

        assertEquals(user, optional.get());
    }

    @Test
    public void getUserByEmailWithNotExistingEmailReturnsNullOptional() {
        Optional<User> optional = logic.getUserByEmail(NOT_EXISTING_EMAIL);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    public void getUserByEmailWithExistingEmailReturnsExistingUser() {
        Optional<User> optional = logic.getUserByEmail(EXISTING_EMAIL);
        User user = new User.Builder()
                .setUserId(EXISTING_ID1)
                .setEmail(EXISTING_EMAIL)
                .setPassword(PASSWORD)
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setDiscount(0)
                .setPhone("38066 000 00 00")
                .setRole(USER)
                .getInstance();

        assertEquals(user, optional.get());
    }

    @Test
    public void saveImageShouldNotThrowExceptionsIfPhotoPassed() throws Exception {
        byte[] image = new byte[]{1, 2, 3};
        logic.saveImage(EXISTING_ID1, image, PHOTO);
    }

    @Test
    public void saveImageShouldNotThrowExceptionsIfPassportPassed() throws Exception {
        byte[] image = new byte[]{1, 2, 3};
        logic.saveImage(EXISTING_ID1, image, PASSPORT_SCAN);
    }

    @Test
    public void getImageShouldReturnImage() throws Exception {
        Optional<byte[]> optional = logic.getImage(EXISTING_ID2, PHOTO);
        assertNotNull(optional.get());
    }

    @Test
    public void getImageShouldReturnNullImage() throws Exception {
        Optional<byte[]> optional = logic.getImage(EXISTING_ID1, PHOTO);
        assertNull(null, optional.orElse(null));
    }
}
