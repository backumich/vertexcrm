package ua.com.vertex.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserDaoTest {

    private final String MSG = "Maybe method was changed";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private User user;

    @Autowired
    private UserDaoInf userDao;

    private static final int EXISTING_ID1 = 22;
    private static final int EXISTING_ID2 = 33;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String EXISTING_EMAIL = "22@test.com";
    private static final String EXISTING_FIRST_NAME = "FirstName";
    private static final String EXISTING_LAST_NAME = "LastName";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";
    private static final String WRONG_IMAGE_TYPE = "wrongImageType";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Before
    public void setUp() throws Exception {
        user = new User.Builder().setUserId(EXISTING_ID1).setEmail(EXISTING_EMAIL).setFirstName(EXISTING_FIRST_NAME)
                .setLastName(EXISTING_LAST_NAME).setDiscount(0).getInstance();
    }

    @Test
    public void jdbcTemplateShouldNotBeNull() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    @WithMockUser
    public void getUserReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUser(EXISTING_ID1);
        assertEquals(EXISTING_ID1, optional.get().getUserId());
    }

    @Test
    @WithMockUser
    public void getUserReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUser(NOT_EXISTING_ID);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    @WithMockUser
    public void getUserByEmailReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUserByEmail(EXISTING_EMAIL);
        assertEquals(EXISTING_ID1, optional.get().getUserId());
    }

    @Test
    @WithMockUser
    public void getUserByEmailReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUserByEmail(NOT_EXISTING_EMAIL);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    public void logInReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.logIn(EXISTING_EMAIL);
        assertEquals(EXISTING_EMAIL, optional.get().getEmail());
    }

    @Test
    public void logInReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.logIn(NOT_EXISTING_EMAIL);
        assertEquals(new User(), optional.orElse(new User()));
    }

    @Test
    public void getListUsersNotEmpty() throws Exception {
        List<User> users = userDao.getAllUsers();
        assertEquals(false, users.isEmpty());
    }

    @Test
    public void getUserDetailsByIDForUserExistingInDatabase() throws Exception {
        User testUser = new User();
        testUser.setUserId(10);
        testUser.setEmail("emailTest");
        testUser.setFirstName("first_name");
        testUser.setLastName("last_name");
        testUser.setDiscount(0);
        testUser.setPhone("666666666");

        Optional<User> optional = userDao.getUserDetailsByID(10);
        Assert.assertNotNull(optional.get());

        Assert.assertEquals(testUser.getUserId(), optional.get().getUserId());
        Assert.assertEquals(testUser.getEmail(), optional.get().getEmail());
        Assert.assertEquals(testUser.getFirstName(), optional.get().getFirstName());
        Assert.assertEquals(testUser.getLastName(), optional.get().getLastName());
        Assert.assertEquals(testUser.getDiscount(), optional.get().getDiscount());
        Assert.assertEquals(testUser.getPhone(), optional.get().getPhone());
    }

    @Test
    @WithMockUser
    public void saveImageNotThrowsExceptionIfSuccessfulPhotoSave() throws Exception {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, PHOTO);
    }

    @Test
    @WithMockUser
    public void saveImageNotThrowsExceptionIfSuccessfulPassportSave() throws Exception {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, PASSPORT_SCAN);
    }

    @Test(expected = RuntimeException.class)
    @WithMockUser
    public void saveImageThrowsExceptionIfWrongImageType() throws Exception {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, WRONG_IMAGE_TYPE);
    }

    @Test
    @WithMockUser
    public void getImageReturnsImageOptionalIfSuccessfulPhoto() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID1, PHOTO);
        assertNotNull(optional.get());
    }

    @Test
    @WithMockUser
    public void getImageReturnsImageOptionalIfSuccessfulPassportScan() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID1, PASSPORT_SCAN);
        assertNotNull(optional.get());
    }

    @Test
    @WithMockUser
    public void getImageReturnsNullOptionalIfNotExistingImage() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID2, PHOTO);
        assertEquals(null, optional.orElse(null));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @WithMockUser
    public void getImageThrowsEmptyResultDataAccessExceptionIfNotExistingUser() {
        userDao.getImage(NOT_EXISTING_ID, PHOTO);
    }

    @Test(expected = RuntimeException.class)
    public void getImageThrowsExceptionIfWrongImageType() throws Exception {
        userDao.getImage(EXISTING_ID1, WRONG_IMAGE_TYPE);
    }

    @Test
    public void searchUserReturnEmtyResult() throws Exception {
        List<User> users = userDao.searchUser("TTTTTTTTT");
        assertNotNull(MSG, users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void searchUserReturnCorrectData() throws Exception {
        List<User> users = userDao.searchUser("Name");
        assertFalse(MSG, users.isEmpty());
        assertEquals(MSG, users.size(), 4);
        assertEquals(MSG, users.get(1), user);

    }

    @Test
    @Transactional
    public void addUserForCreateCertificateReturnCorrectData() throws Exception {
        User userForTest = new User.Builder().setEmail("email33").setFirstName("Test")
                .setLastName("Test").setRole(Role.USER).getInstance();
        int result = userDao.addUserForCreateCertificate(userForTest);
        userForTest.setUserId(result);
        assertEquals(MSG, userForTest, userDao.getUser(result).get());
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void addUserForCreateCertificateReturnExc() throws Exception {
        userDao.addUserForCreateCertificate(user);
    }
}
