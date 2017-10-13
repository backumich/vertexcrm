package ua.com.vertex.controllers;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.sql.SQLException;

@Controller
public class ImageController {
    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);
    private static final String USER_PROFILE = "userProfile";
    private static final String IMAGE = "image";
    private static final String IMAGE_PHOTO = "imagePhoto";
    private static final String IMAGE_PASSPORT = "imagePassport";
    private static final String IMAGE_TYPE = "imageType";
    private static final String USER = "user";
    private static final String IMAGE_ERROR = "imageError";

    private final UserLogic userLogic;

    @PostMapping(value = "/showImagePhoto")
    public String showImagePhoto(@ModelAttribute(USER) User user,
                            @RequestParam(IMAGE_TYPE) String imageType, Model model) throws SQLException {

        LOGGER.debug(IMAGE_PHOTO + " page accessed");
        encode(model, user.getEmail(), imageType);
        model.addAttribute(USER, user);
        LOGGER.debug("Passing imagePhoto to JSP");

        return IMAGE_PHOTO;
    }

    @PostMapping(value = "/showImagePassport")
    @PreAuthorize("(principal.username).equals(#user.email) || hasRole('ADMIN')")
    public String showImagePassport(@ModelAttribute(USER) User user,
                                    @RequestParam(IMAGE_TYPE) String imageType, Model model) throws SQLException {

        LOGGER.debug(IMAGE_PASSPORT + " page accessed");
        encode(model, user.getEmail(), imageType);
        model.addAttribute(USER, user);
        LOGGER.debug("Passing imagePassport to JSP");

        return IMAGE_PASSPORT;
    }

    private void encode(Model model, String email, String imageType) throws SQLException {
        String encoded = Base64.encodeBase64String(userLogic.getImage(email, imageType).orElse(new byte[]{}));
        model.addAttribute(imageType, encoded);
    }

    @PostMapping(value = "/uploadImage")
    @PreAuthorize("(principal.username).equals(#user.email)")
    public String uploadImage(@ModelAttribute(USER) User user,
                              @RequestPart(value = IMAGE, required = false) byte[] image,
                              @RequestParam(IMAGE_TYPE) String imageType, Model model) {
        String view = USER_PROFILE;

        if (image == null) {
            view = IMAGE_ERROR;
            LOGGER.debug("no image selected");
        } else {
            userLogic.saveImage(user.getEmail(), image, imageType);
            model.addAttribute(user);
        }
        return view;
    }

    @Autowired
    public ImageController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}
