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
import ua.com.vertex.utils.Storage;

@Controller
public class ImageController {

    private final UserLogic userLogic;
    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);

    private static final String LOG_PHOTO = "Passing user photo to JSP";
    private static final String LOG_PASSPORT_SCAN = "Passing user passport scan to JSP";

    private static final String USER_PAGE = "userProfile";
    private static final String IMAGE = "image";
    private static final String ERROR = "error";
    private static final String IMAGE_ERROR = "imageError";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";

    @RequestMapping(value = "/userPhoto")
    public String showUserPhoto(@RequestParam("previousPage") String previousPage,
                                @RequestParam("userId") int userId, Model model) {

        String view = IMAGE;
        try {
            encode(model, userId, previousPage, PHOTO);
            LOGGER.debug(storage.getId() + LOG_PHOTO);
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/passportScan")
    public String showPassportScan(@RequestParam("previousPage") String previousPage,
                                   @RequestParam("userId") int userId, Model model) {

        String view = IMAGE;
        try {
            encode(model, userId, previousPage, PASSPORT_SCAN);
            LOGGER.debug(storage.getId() + LOG_PASSPORT_SCAN);
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    private void encode(Model model, int userId, String previousPage, String imageType) {
        String encoded = Base64.encode(userLogic.getImage(userId, imageType).orElse(new byte[]{}));
        model.addAttribute(imageType, encoded);
        model.addAttribute("page", previousPage);
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute("user") User user,
                              @RequestPart(value = "image", required = false) byte[] image,
                              @RequestParam("imageType") String imageType, Model model) {
        String view = USER_PAGE;
        try {
            if (image == null) {
                view = IMAGE_ERROR;
            } else {
                userLogic.saveImage(user.getUserId(), image, imageType);
                model.addAttribute(user);
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @Autowired
    public ImageController(UserLogic userLogic, Storage storage) {
        this.userLogic = userLogic;
        this.storage = storage;
    }
}
