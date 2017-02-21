package ua.com.vertex.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

@Controller
public class ImageController {

    private final UserLogic userLogic;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);

    private static final String USER_PROFILE = "userProfile";
    private static final String PAGE_TO_DISPLAY = "pageToDisplay";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "imageType";
    private static final String ERROR = "error";
    private static final String USER = "user";
    private static final String IMAGE_ERROR = "imageError";

    @RequestMapping(value = "/showImage")
    public String showUserPhoto(@ModelAttribute(USER) User user,
                                @RequestParam(PAGE_TO_DISPLAY) String pageToDisplay,
                                @RequestParam(IMAGE_TYPE) String imageType, Model model) {

        LOGGER.debug(logInfo.getId() + pageToDisplay + " page accessed");
        String view = pageToDisplay;
        try {
            encode(model, user.getUserId(), imageType);
            model.addAttribute(USER, user);
            LOGGER.debug(logInfo.getId() + "Passing image to JSP");
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    private void encode(Model model, int userId, String imageType) {
        String encoded = Base64.encode(userLogic.getImage(userId, imageType).orElse(new byte[]{}));
        model.addAttribute(imageType, encoded);
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute(USER) User user,
                              @RequestPart(value = IMAGE, required = false) byte[] image,
                              @RequestParam(IMAGE_TYPE) String imageType, Model model) {

        String view = USER_PROFILE;
        try {
            if (image == null) {
                view = IMAGE_ERROR;
                LOGGER.debug(logInfo.getId() + "no image selected");
            } else {
                userLogic.saveImage(user.getUserId(), image, imageType);
                model.addAttribute(user);
            }
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e, e);
            view = ERROR;
        }

        return view;
    }

    @Autowired
    public ImageController(UserLogic userLogic, LogInfo logInfo) {
        this.userLogic = userLogic;
        this.logInfo = logInfo;
    }
}
