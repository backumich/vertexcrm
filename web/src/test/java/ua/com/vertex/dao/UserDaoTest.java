package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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


    private static final int EXISTING_ID1 = 22;
    private static final int EXISTING_ID2 = 33;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;
    private static final String EXISTING_EMAIL = "22@test.com";
    private static final String NOT_EXISTING_EMAIL = "notExisting@test.com";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";
    private static final String WRONG_IMAGE_TYPE = "wrongImageType";

    @Test
    public void getUserReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUser(EXISTING_ID1);
        assertEquals(EXISTING_ID1, optional.get().getUserId());
    }

    @Test
    public void getUserReturnsNullUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUser(NOT_EXISTING_ID);
        assertEquals(null, optional.orElse(null));
    }

    @Test
    public void getUserByEmailReturnsUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUserByEmail(EXISTING_EMAIL);
        assertEquals(EXISTING_ID1, optional.get().getUserId());
    }

    @Test
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
    public void saveImageNotThrowsExceptionIfSuccessfulPhotoSave() throws Exception {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, PHOTO);

    }

    @Test
    public void saveImageNotThrowsExceptionIfSuccessfulPassportSave() throws Exception {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, PASSPORT_SCAN);

    }

    @Test(expected = RuntimeException.class)
    public void saveImageThrowsExceptionIfWrongImageType() throws Exception {
        byte[] image = {1};
        userDao.saveImage(EXISTING_ID1, image, WRONG_IMAGE_TYPE);
    }

    @Test
    public void getImageReturnsImageOptionalIfSuccessfulPhoto() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID1, PHOTO);
        assertNotNull(optional.get());
    }

    @Test
    public void getImageReturnsImageOptionalIfSuccessfulPassportScan() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID1, PASSPORT_SCAN);
        assertNotNull(optional.get());
    }

    @Test
    public void getImageReturnsNullOptionalIfNotExistingImage() {
        Optional<byte[]> optional = userDao.getImage(EXISTING_ID2, PHOTO);
        assertEquals(null, optional.orElse(null));
    }

    @Test(expected = DataAccessException.class)
    public void getImageThrowsExceptionForNotExistingUser() {
        userDao.getImage(NOT_EXISTING_ID, PHOTO);
    }

    @Test(expected = RuntimeException.class)
    public void getImageThrowsExceptionIfWrongImageType() throws Exception {
        userDao.getImage(EXISTING_ID1, WRONG_IMAGE_TYPE);
    }
}
