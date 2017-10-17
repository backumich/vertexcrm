package ua.com.vertex.controllers;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;

@Controller
public class ImageController {
    private static final Logger logger = LogManager.getLogger(ImageController.class);

    private static final String USER_PROFILE = "userProfile";
    private static final String PAGE_TO_DISPLAY = "pageToDisplay";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "imageType";
    private static final String USER = "user";
    private static final String IMAGE_ERROR = "imageError";

    private final UserLogic userLogic;

    @GetMapping(value = "/showImage")
    public String showImage(@ModelAttribute(USER) User user,
                            @RequestParam(PAGE_TO_DISPLAY) String pageToDisplay,
                            @RequestParam(IMAGE_TYPE) String imageType, Model model) throws SQLException {

        logger.debug(pageToDisplay + " page accessed");
        encode(model, user.getUserId(), imageType);
        model.addAttribute(USER, user);
        logger.debug("Passing image to JSP");

        return pageToDisplay;
    }

    private void encode(Model model, int userId, String imageType) throws SQLException {
        String encoded = Base64.encodeBase64String(userLogic.getImage(userId, imageType).orElse(new byte[]{}));
        model.addAttribute(imageType, encoded);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/uploadImage")
    public String uploadImage(@ModelAttribute(USER) User user,
                              @RequestPart(value = IMAGE, required = false) byte[] image,
                              @RequestParam(IMAGE_TYPE) String imageType, Model model) {

        String view = USER_PROFILE;
        if (image == null) {
            view = IMAGE_ERROR;
            logger.debug("no image selected");
        } else {
            userLogic.saveImage(user.getUserId(), image, imageType);
            model.addAttribute(user);
        }

        return view;
    }

    @Autowired
    public ImageController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}
