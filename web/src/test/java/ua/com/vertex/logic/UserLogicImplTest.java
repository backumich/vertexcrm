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
import ua.com.vertex.dao.UserDaoImpl;
import ua.com.vertex.utils.Storage;

import java.util.Optional;

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

    @Mock
    private Storage storage;

    private UserLogicImpl logic;

    private static final String EXISTING_EMAIL = "33@test.com";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";
    private static final int EXISTING_ID = 33;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String PASSWORD = "password";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logic = new UserLogicImpl(dao, storage);
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
    public void getUserByIdWithNotExistingIdReturnsEmptyUser() {
        Optional<User> optional = logic.getUserById(NOT_EXISTING_ID);
        assertEquals(EMPTY_USER, optional.get());
    }

    @Test
    public void getUserByIdWithExistingIdReturnsExistingUser() {
        Optional<User> optional = logic.getUserById(EXISTING_ID);
        User user = new User.Builder()
                .setUserId(EXISTING_ID)
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
    public void getUserByEmailWithNotExistingEmailReturnsEmptyUser() {
        Optional<User> optional = logic.getUserByEmail(NOT_EXISTING_EMAIL);
        assertEquals(EMPTY_USER, optional.get());
    }

    @Test
    public void getUserByEmailWithExistingEmailReturnsExistingUser() {
        Optional<User> optional = logic.getUserByEmail(EXISTING_EMAIL);
        User user = new User.Builder()
                .setUserId(EXISTING_ID)
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
    public void imagesCheckForUserWithNullImagesReturnsUserWithNullImages() {
        User user = new User();
        assertEquals(user, logic.imagesCheck(user));
    }

    @Test
    public void imagesCheckForUserWithNotNullImagesReturnsUserWithSpecifiedByteArray() {
        byte[] dataStart = {1, 2, 3, 4};
        byte[] dataFinal = {1};
        User userStart = new User.Builder()
                .setPhoto(dataStart)
                .setPassportScan(dataStart)
                .getInstance();
        User userFinal = new User.Builder()
                .setPhoto(dataFinal)
                .setPassportScan(dataFinal)
                .getInstance();

        assertEquals(userFinal, logic.imagesCheck(userStart));
    }
}
