package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static ua.com.vertex.beans.Role.USER;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserLogicImplTest {

    @Autowired
    private UserDaoInf userDao;

    private UserLogicImpl userLogic;

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
        userLogic = new UserLogicImpl(userDao);
    }

    @Test
    @WithMockUser
    public void getUserByIdWithNotExistingIdReturnsNullOptional() {
        Optional<User> optional = userLogic.getUserById(NOT_EXISTING_ID);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    @WithMockUser
    public void getUserByIdWithExistingIdReturnsExistingUser() {
        Optional<User> optional = userLogic.getUserById(EXISTING_ID1);
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
    @WithMockUser
    public void getUserByEmailWithNotExistingEmailReturnsNullOptional() {
        Optional<User> optional = userLogic.getUserByEmail(NOT_EXISTING_EMAIL);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    @WithMockUser
    public void getUserByEmailWithExistingEmailReturnsExistingUser() {
        Optional<User> optional = userLogic.getUserByEmail(EXISTING_EMAIL);
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
    @WithMockUser
    public void saveImageShouldNotThrowExceptionsIfPhotoPassed() throws Exception {
        byte[] image = new byte[]{1, 2, 3};
        userLogic.saveImage(EXISTING_ID2, image, PHOTO);
    }

    @Test
    @WithMockUser
    public void saveImageShouldNotThrowExceptionsIfPassportPassed() throws Exception {
        byte[] image = new byte[]{1, 2, 3};
        userLogic.saveImage(EXISTING_ID2, image, PASSPORT_SCAN);
    }

    @Test
    @WithMockUser
    public void getImageShouldReturnImage() throws Exception {
        Optional<byte[]> optional = userLogic.getImage(EXISTING_ID2, PHOTO);
        assertNotNull(optional.get());
    }

    @Test
    @WithMockUser
    public void getImageShouldReturnNullImage() throws Exception {
        Optional<byte[]> optional = userLogic.getImage(EXISTING_ID1, PHOTO);
        assertNull(null, optional.orElse(null));
    }

    @Test
    public void getAllUserIdsCalledInUserDaoAndReturnNotNull() throws Exception {
        assertNotNull(userLogic.getAllUserIds());
        verify(userDao).getAllUserIds();
    }

    @Test
    public void searchUserCalledInUserDaoAndReturnNotNull() throws Exception {
        assertNotNull(userLogic.searchUser("test"));
        verify(userDao).searchUser("test");

    }
}