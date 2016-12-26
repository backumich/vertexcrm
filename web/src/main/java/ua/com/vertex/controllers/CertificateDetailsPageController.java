package ua.com.vertex.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

@Controller
public class CertificateDetailsPageController {
    private final CertDetailsPageLogic logic;
    private final ImageStorage storage;

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);
    private static final String LOG_PHOTO = "Passing user photo to JSP";
    private static final String LOG_PROCESS = "Processing request with certificateId=";
    private static final String LOG_INVALID_DATA = "Requested data is invalid";
    private static final String LOG_PASS_DATA = "Passing certificate and user data to JSP";

    private static final String PAGE_JSP = "certificateDetails";
    private static final String ERROR_JSP = "error";
    private static final String PHOTO_JSP = "photo";

    @RequestMapping(value = "/" + PAGE_JSP)
    public String showCertificateDetailsPage(Model model) {
        try {
            model.addAttribute(new Certificate());
        } catch (Throwable t) {
            LOGGER.error(t, t);
            return ERROR_JSP;
        }
        return PAGE_JSP;
    }

    @RequestMapping(value = "/processCertificateDetails")
    public String processCertificateDetails(@Validated @ModelAttribute("certificate") Certificate certificate,
                                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Entered value must be > 0");
            LOGGER.info(LOG_INVALID_DATA);
            return PAGE_JSP;
        }

        try {
            int certificationId = certificate.getCertificationId();
            LOGGER.info(LOG_PROCESS + certificationId);
            certificate = logic.getCertificateDetails(certificationId);
            User user = logic.getUserDetails(certificate.getUserId());
            setModel(certificate, user, model);
        } catch (Throwable t) {
            LOGGER.error(t, t);
            return ERROR_JSP;
        }

        LOGGER.info(LOG_PASS_DATA);
        return PAGE_JSP;
    }

    private void setModel(Certificate certificate, User user, Model model) {
        if (certificate.getCertificationId() != 0) {
            model.addAttribute("certificate", certificate);
        } else {
            model.addAttribute("error", "No certificate with this ID! Try again!");
            return;
        }

        if (user.getUserId() != 0) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "No holder is assigned to this certificate ID!");
        }
    }

    @RequestMapping(value = "/showUserPhoto")
    public String showUserPhoto(Model model) {
        try {
            byte[] userPhoto = storage.getImageData();
            String encodedImage = Base64.encode(userPhoto);
            model.addAttribute("image", encodedImage);
        } catch (Throwable t) {
            LOGGER.error(t, t);
            return ERROR_JSP;
        }
        LOGGER.info(LOG_PHOTO);
        return PHOTO_JSP;
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic, ImageStorage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
