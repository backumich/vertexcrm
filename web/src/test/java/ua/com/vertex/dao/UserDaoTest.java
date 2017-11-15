package ua.com.vertex.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.controllers.exceptionHandling.UpdatedPasswordNotSaved;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.DataNavigator;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

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
    private static final int EXISTING_ID2 = 34;
    private static final int EXISTING_ID3 = 2;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    @SuppressWarnings("WeakerAccess")
    static final String EXISTING_EMAIL = "22@test.com";
    static final String EXISTING_EMAIL3 = "email1@test.com";
    private static final String EXISTING_PASSWORD = "111111";
    private static final String EXISTING_FIRST_NAME = "FirstName";
    private static final String EXISTING_LAST_NAME = "LastName";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";
    private static final String WRONG_IMAGE_TYPE = "wrongImageType";

    @Autowired
    public void setDataSource(@Qualifier(value = "DS") DataSource dataSource) {
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
        assertEquals(EXISTING_ID1, optional.orElse(new User.Builder().setUserId(EXISTING_ID1).getInstance()).getUserId());
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
        Optional<User> optional = userDao.getUserByEmail(EXISTING_EMAIL3);
        assertEquals(EXISTING_ID3, optional.orElse(new User.Builder().setUserId(EXISTING_ID3).getInstance())
                .getUserId());
    }

    @Test
    @WithMockUser
    public void getUserByEmailReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUserByEmail(NOT_EXISTING_EMAIL);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    public void logInReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.logIn(EXISTING_EMAIL3);
        assertEquals(EXISTING_EMAIL3, optional.orElse(new User.Builder().setUserId(EXISTING_ID3).getInstance())
                .getEmail());
    }

    @Test
    public void logInReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.logIn(NOT_EXISTING_EMAIL);
        assertEquals(new User(), optional.orElse(new User()));
    }

    @Test
    public void getListUsersNotEmpty() throws Exception {
        List<User> users = userDao.getUsersPerPages(new DataNavigator());
        assertEquals(false, users.isEmpty());
    }

    @Test
    @WithMockUser
    public void saveImageNotThrowsExceptionIfSuccessfulPhotoSave() {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, PHOTO);
    }

    @Test
    @WithMockUser
    public void saveImageNotThrowsExceptionIfSuccessfulPassportSave() {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, PASSPORT_SCAN);
    }

    @Test(expected = RuntimeException.class)
    @WithMockUser
    public void saveImageThrowsExceptionIfWrongImageType() {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, WRONG_IMAGE_TYPE);
    }

    @Test
    @WithMockUser
    public void getImageReturnsImageOptionalIfSuccessfulPhoto() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID1, PHOTO);
        assertNotNull(optional.orElse(null));
    }

    @Test
    @WithMockUser
    public void getImageReturnsImageOptionalIfSuccessfulPassportScan() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID1, PASSPORT_SCAN);
        assertNotNull(optional.orElse(null));
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
        users.forEach(user1 -> assertTrue(MSG, user1.getEmail().contains("Name")
                || user1.getFirstName().contains("Name") || user1.getLastName().contains("Name")));
    }

    @Test
    @WithMockUser
    @Transactional
    public void addUserForCreateCertificateReturnCorrectData() throws Exception {
        User userForTest = new User.Builder().setEmail("email33").setFirstName("Test")
                .setLastName("Test").setRole(Role.ROLE_USER).getInstance();
        int result = userDao.addUserForCreateCertificate(userForTest);
        userForTest.setUserId(result);
        assertEquals(MSG, userForTest, userDao.getUser(result).orElse(null));
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void addUserForCreateCertificateReturnExc() throws Exception {
        userDao.addUserForCreateCertificate(user);
    }

    @Test
    public void userForRegistrationCheckReturnEmptyOptional() throws Exception {
        assertFalse(MSG, userDao.userForRegistrationCheck("test@test.com").isPresent());
    }

    @Test
    public void userForRegistrationCheckReturnCorrectData() throws Exception {
        assertEquals(MSG, new User.Builder().setEmail(EXISTING_EMAIL).setIsActive(false).getInstance()
                , userDao.userForRegistrationCheck(EXISTING_EMAIL).orElse(null));
    }

    @Test(expected = DataAccessException.class)
    public void registrationUserInsertEmptyUser() throws Exception {
        userDao.registrationUserInsert(new User());
    }

    @Test
    public void registrationUserInsertCorrectInsert() throws Exception {
        User userForInsert = new User.Builder().setEmail("testInsert@Test.com").setPassword(EXISTING_PASSWORD).
                setFirstName(EXISTING_FIRST_NAME).setLastName(EXISTING_LAST_NAME).setDiscount(0).setPhone("0933333333")
                .setRole(Role.ROLE_USER).getInstance();
        userDao.registrationUserInsert(userForInsert);
        User userForCheck = userDao.getUserByEmail("testInsert@Test.com").orElse(null);
        assert userForCheck != null;
        userForInsert.setUserId(userForCheck.getUserId());
        assertEquals(MSG, userForInsert, userForCheck);
    }

    @Test
    public void registrationUserCorrectUpdate() throws Exception {
        User userForUpdate = new User.Builder().setUserId(EXISTING_ID2).setEmail("34@test.com").setPassword("test")
                .setFirstName("test").setLastName("test").setPhone("0933333333").setRole(Role.ROLE_USER).setIsActive(false).getInstance();
        assertNotEquals(MSG, userForUpdate, userDao.getUserByEmail("34@test.com").orElse(null));
        userDao.registrationUserUpdate(userForUpdate);
        assertEquals(MSG, userForUpdate, userDao.getUserByEmail("34@test.com").orElse(null));
    }

    @Test
    public void getCourseUsersReturnCorrectData() throws Exception {
        assertEquals("Maybe method was changed", userDao.getCourseUsers(1).get(0),
                new User.Builder().setUserId(1).setEmail("email1").setFirstName("FirstName")
                        .setLastName("LastName").setDiscount(0).getInstance());

    }

    @Test
    public void getTeachersReturnCorrectData() throws Exception {
        List<User> teachers = userDao.getTeachers();
        assertFalse(MSG, teachers.isEmpty());
        teachers.forEach(teacher1 -> {
            System.out.println(teacher1);
            assertTrue(teacher1.getRole().equals(Role.ROLE_TEACHER) && teacher1.isActive());
        });
    }

    @Test
    @Transactional
    public void setParamsToRestorePasswordReturnsGeneratedId() {
        final String email = "email@email.com";
        final String uuid = "uuid";
        final LocalDateTime dateTime = LocalDateTime.now();

        long result = userDao.setParamsToRestorePassword(email, uuid, dateTime);
        assertTrue(result > 2);
    }

    @Test
    public void getEmailByUuidReturnsPasswordResetDto() {
        final int id = 1;
        final String email = "email1@email.com";
        final String uuid = "06e668ba-d4c1-4f3e-8bea-5935929120c5";
        final LocalDateTime dateTime = LocalDateTime.of(2017, 10, 9, 0, 0, 0);
        final PasswordResetDto dto = passwordResetDtoHelper(email, dateTime);

        PasswordResetDto result = userDao.getEmailByUuid(id, uuid);
        assertEquals(dto, result);
    }

    private PasswordResetDto passwordResetDtoHelper(String email, LocalDateTime dateTime) {
        return PasswordResetDto.builder()
                .email(email)
                .creationTime(dateTime)
                .build();
    }

    @Test(expected = RuntimeException.class)
    public void getEmailByUuidThrowsException() {
        final int notExistingId = 100;
        final String uuid = "06e668ba-d4c1-4f3e-8bea-5935929120c5";

        userDao.getEmailByUuid(notExistingId, uuid);
    }

    @Test
    @Transactional
    public void savePasswordOk() {
        final String email = "email1";
        final String password = "new password";

        userDao.savePassword(email, password);
    }

    @Test(expected = UpdatedPasswordNotSaved.class)
    public void savePasswordThrowsException() {
        final String email = "not existing email";
        final String password = "new password";

        userDao.savePassword(email, password);
    }
}
