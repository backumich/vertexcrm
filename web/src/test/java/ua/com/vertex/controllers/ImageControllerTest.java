package ua.com.vertex.controllers;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.ui.Model;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ImageControllerTest {

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private LogInfo logInfo;

    @Mock
    private Model model;

    private ImageController controller;
    private User user;

    private static final int EXISTING_ID = 22;
    private static final byte[] PHOTO_RETRIEVED = {100};
    private static final String PHOTO = "photo";
    private static final String PAGE_TO_DISPLAY = "pageToDisplay";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ImageController(userLogic, logInfo);
        user = new User.Builder().setUserId(EXISTING_ID).getInstance();
    }

    @Test
    @WithMockUser
    public void showImageAddsModelAttributePhoto() {
        controller.showImage(user, PAGE_TO_DISPLAY, PHOTO, model);
        verify(model, times(1)).addAttribute(PHOTO, Base64.encodeBase64String(PHOTO_RETRIEVED));
    }

    @Test
    @WithMockUser
    public void uploadImageAddsModelAttributeUser() throws Exception {
        controller.uploadImage(user, new byte[]{100}, PHOTO, model);
        verify(model, times(1)).addAttribute(user);
    }
}
