package ua.com.vertex.controllers;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;

import java.sql.SQLException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ImageControllerTest {

    @Autowired
    private ImageController controller;

    @Mock
    private Model model;

    private User user;

    private static final String EXISTING_EMAIL = "22@test.com";
    private static final byte[] IMAGE_RETRIEVED = {100};
    private static final String PHOTO = "photo";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User.Builder().setEmail(EXISTING_EMAIL).getInstance();
    }

    @Test
    public void showImagePhotoAddsModelAttributePhoto() throws SQLException {
        controller.showImagePhoto(user, PHOTO, model);
        verify(model, times(1)).addAttribute(PHOTO, Base64.encodeBase64String(IMAGE_RETRIEVED));
    }

    @Test
    @WithMockUser(username = EXISTING_EMAIL)
    public void showImagePassportAddsModelAttributePhoto() throws SQLException {
        controller.showImagePassport(user, PHOTO, model);
        verify(model, times(1)).addAttribute(PHOTO, Base64.encodeBase64String(IMAGE_RETRIEVED));
    }

    @Test(expected = IllegalArgumentException.class)
    @WithAnonymousUser
    public void showImagePassportFailsForAnonymousUser() throws SQLException {
        controller.showImagePassport(user, PHOTO, model);
    }

    @Test
    @WithMockUser(username = EXISTING_EMAIL)
    public void uploadImageAddsModelAttributeUser() throws Exception {
        controller.uploadImage(user, new byte[]{100}, PHOTO, model);
        verify(model, times(1)).addAttribute(user);
    }

    @Test(expected = IllegalArgumentException.class)
    @WithAnonymousUser
    public void uploadImageAddsModelFailsForAnonymousUser() throws Exception {
        controller.uploadImage(user, new byte[]{100}, PHOTO, model);
    }
}
