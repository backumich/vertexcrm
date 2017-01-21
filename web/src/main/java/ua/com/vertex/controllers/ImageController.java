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

    @RequestMapping(value = "/userPhoto")
    public String showUserPhoto(@RequestParam("previousPage") String previousPage, Model model) {
        String view = IMAGE;
        try {
            byte[] userPhoto = storage.getPhoto();
            String encodedImage = Base64.encode(userPhoto);
            model.addAttribute("photo", encodedImage);
            model.addAttribute("page", previousPage);

            LOGGER.debug(storage.getId() + LOG_PHOTO);

        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/passportScan")
    public String showPassportScan(@RequestParam("previousPage") String previousPage, Model model) {
        String view = IMAGE;
        try {
            byte[] passportScan = storage.getPassportScan();
            String encodedImage = Base64.encode(passportScan);
            model.addAttribute("passportScan", encodedImage);
            model.addAttribute("page", previousPage);

            LOGGER.debug(storage.getId() + LOG_PASSPORT_SCAN);

        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            view = ERROR;
        }

        return view;
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
                user.setPhoto(storage.getPhoto());
                user.setPassportScan(storage.getPassportScan());
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
