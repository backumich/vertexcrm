package ua.com.vertex.controllers;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.controllers.exceptionHandling.exceptions.MultipartValidationException;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application.properties")
@Transactional
public class ImageControllerTest {

    @Autowired
    private ImageController controller;

    @Mock
    private Model model;

    private User user;

    private static final String EXISTING_EMAIL = "22@test.com";
    private static final String NOT_EXISTING_EMAIL = "@test.com";
    private static final byte[] IMAGE_AS_BYTES = {100};
    private static final String PHOTO = "photo";

    @Value("${image.size.bytes}")
    private int fileSizeInBytes;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User.Builder().setEmail(EXISTING_EMAIL).getInstance();
    }

    @Test
    public void showImagePhotoAddsModelAttributePhoto() throws SQLException {
        controller.showImagePhoto(user, PHOTO, model);
        verify(model, times(1)).addAttribute(PHOTO, Base64.encodeBase64String(IMAGE_AS_BYTES));
    }

    @Test
    @WithMockUser(username = EXISTING_EMAIL)
    public void showImagePassportAddsModelAttributePhoto() throws SQLException {
        controller.showImagePassport(user, PHOTO, model);
        verify(model, times(1)).addAttribute(PHOTO, Base64.encodeBase64String(IMAGE_AS_BYTES));
    }

    @Test(expected = IllegalArgumentException.class)
    @WithAnonymousUser
    public void showImagePassportFailsForAnonymousUser() throws SQLException {
        controller.showImagePassport(user, PHOTO, model);
    }

    @Test
    @WithMockUser(username = EXISTING_EMAIL)
    public void uploadImageReturnsViewForUserOwner() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "file", "image/jpg", IMAGE_AS_BYTES);
        String view = controller.uploadImage(user, file, PHOTO);
        assertEquals("userProfile", view);
    }

    @Test
    @WithMockUser(username = EXISTING_EMAIL)
    public void uploadImage() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "file", "image/jpg", IMAGE_AS_BYTES);
        String view = controller.uploadImage(user, file, PHOTO);
        assertEquals("userProfile", view);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void uploadImageAddsModelAttributeUser() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "file", "image/jpg", IMAGE_AS_BYTES);
        String view = controller.uploadImage(user, file, PHOTO);
        assertEquals("userProfile", view);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = NOT_EXISTING_EMAIL)
    public void uploadImageThrowsAccessDeniedForWrongUser() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "file", "image/jpg", new byte[]{});
        controller.uploadImage(user, file, PHOTO);
    }

    @Test(expected = MultipartValidationException.class)
    @WithMockUser(username = EXISTING_EMAIL)
    public void uploadImageThrowsExceptionIfSizeExceeded() throws Exception {
        byte[] image = new byte[fileSizeInBytes + 1];
        Arrays.fill(image, (byte) 1);
        MultipartFile file = new MockMultipartFile("file", "file", "image/jpg", image);
        controller.uploadImage(user, file, PHOTO);
    }

    @Test(expected = MultipartValidationException.class)
    @WithMockUser(username = EXISTING_EMAIL)
    public void uploadImageThrowsExceptionIfInvalidFileType() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "file", "wrongFileType/jpg", IMAGE_AS_BYTES);
        controller.uploadImage(user, file, PHOTO);
    }

    @Test(expected = IllegalArgumentException.class)
    @WithAnonymousUser
    public void uploadImageAddsModelFailsForAnonymousUser() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "file", "image/jpg", IMAGE_AS_BYTES);
        controller.uploadImage(user, file, PHOTO);
    }
}
