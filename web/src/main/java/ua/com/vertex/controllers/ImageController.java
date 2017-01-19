package ua.com.vertex.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.utils.Storage;

@Controller
public class ImageController {

    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);

    private static final String LOG_PHOTO = "Passing user photo to JSP";
    private static final String LOG_PASSPORT_SCAN = "Passing user passport scan to JSP";

    private static final String IMAGE = "image";
    private static final String ERROR = "error";

    @RequestMapping(value = "/userPhoto")
    public String showUserPhoto(@RequestParam("previousPage") String previousPage, Model model) {
        String returnPage = IMAGE;
        try {
            byte[] userPhoto = storage.getPhoto();
            String encodedImage = Base64.encode(userPhoto);
            model.addAttribute("photo", encodedImage);
            model.addAttribute("page", previousPage);

            LOGGER.debug(storage.getId() + LOG_PHOTO);

        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            returnPage = ERROR;
        }

        return returnPage;
    }

    @RequestMapping(value = "/passportScan")
    public String showPassportScan(Model model) {
        String returnPage = IMAGE;
        try {
            byte[] passportScan = storage.getPassportScan();
            String encodedImage = Base64.encode(passportScan);
            model.addAttribute("passportScan", encodedImage);

            LOGGER.debug(storage.getId() + LOG_PASSPORT_SCAN);

        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            returnPage = ERROR;
        }

        return returnPage;
    }

    @Autowired
    public ImageController(Storage storage) {
        this.storage = storage;
    }
}
