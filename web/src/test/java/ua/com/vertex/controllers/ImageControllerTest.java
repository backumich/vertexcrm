package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ImageControllerTest {

    @Mock
    private UserLogic userLogic;

    @Mock
    private LogInfo logInfo;

    @Mock
    private Model model;

    private ImageController controller;

    private static final int EXISTING_ID = 22;
    private static final String PHOTO = "photo";
    private static final String PREVIOUS_PAGE = "previousPage";
    private static final String PAGE = "page";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ImageController(userLogic, logInfo);
    }

//    @Test
//    public void showUserPhotoAddsModelAttributePhoto() {
//        Optional<byte[]> optional = Optional.of(new byte[]{1});
//        String encoded = Base64.encode(optional.get());
//
//        when(userLogic.getImage(22, PHOTO)).thenReturn(optional);
//
//        controller.showUserPhoto(PREVIOUS_PAGE, EXISTING_ID, model);
//        verify(model, times(1)).addAttribute(PHOTO, encoded);
//    }
//
//
//    @Test
//    public void showUserPhotoAddsModelAttributePreviousPage() {
//        Optional<byte[]> optional = Optional.of(new byte[]{1});
//
//        when(userLogic.getImage(22, PHOTO)).thenReturn(optional);
//
//        controller.showUserPhoto(PREVIOUS_PAGE, EXISTING_ID, model);
//        verify(model, times(1)).addAttribute(PAGE, PREVIOUS_PAGE);
//    }

    @Test
    public void uploadImageAddsModelAttributeUser() throws Exception {
        User user = new User.Builder().setUserId(EXISTING_ID).getInstance();
        byte[] image = {1};

        controller.uploadImage(user, image, PHOTO, model);
        verify(model, times(1)).addAttribute(user);
    }
}
