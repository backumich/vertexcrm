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
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.utils.Storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CertificateDetailsPageController {
    private final CertDetailsPageLogic logic;
    private final Storage storage;

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);
    private static final String LOG_PHOTO = "Passing user photo to JSP";
    private static final String LOG_PROCESS = "Processing request with certificateId=";
    private static final String LOG_INVALID_DATA = "Requested data is invalid";
    private static final String LOG_PASS_DATA = "Passing certificate and user data to JSP";

    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String ERROR = "error";
    private static final String PHOTO = "certificateHolderPhoto";

    @RequestMapping(value = "/" + CERTIFICATE_DETAILS)
    public String showCertificateDetailsPage(Model model, HttpServletRequest request) throws Exception {
        String returnPage = CERTIFICATE_DETAILS;
        try {
            HttpSession session = request.getSession();
            storage.setSessionId(session.getId());
            model.addAttribute("newCertificate", new Certificate());
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            returnPage = ERROR;
        }
        return returnPage;
    }

    @RequestMapping(value = "/processCertificateDetails")
    public String processCertificateDetails(@Validated @ModelAttribute("certificate") Certificate certificate,
                                            BindingResult result, Model model) {
        String returnPage = CERTIFICATE_DETAILS;
        try {
            if (result.hasErrors()) {
                model.addAttribute("error", "Entered value must be a positive integer!");
                LOGGER.info(storage.getId() + LOG_INVALID_DATA);
            } else {
                int certificationId = certificate.getCertificationId();

                LOGGER.info(storage.getId() + LOG_PROCESS + certificationId);

                certificate = getCertificateDetails(certificationId);
                if (!Certificate.EMPTY_CERTIFICATE.equals(certificate)) {
                    model.addAttribute("certificate", certificate);
                    User user = getUserDetails(certificate);
                    model.addAttribute("user", user);
                } else {
                    model.addAttribute("error", "No certificate with this ID!");
                }

                LOGGER.info(storage.getId() + LOG_PASS_DATA);
            }
        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            returnPage = ERROR;
        }

        return returnPage;
    }

    private Certificate getCertificateDetails(int certificationId) {
        return logic.getCertificateDetails(certificationId).orElse(Certificate.EMPTY_CERTIFICATE);
    }

    private User getUserDetails(Certificate certificate) {
        User user = logic.getUserDetails(certificate.getUserId()).orElse(User.EMPTY_USER);
        if (user.getPhoto() != null) {
            storage.setPhoto(user.getPhoto());
            user.setPhoto(new byte[]{(byte) 1});
        }
        return user;
    }

    @RequestMapping(value = "/" + PHOTO)
    public String showUserPhoto(Model model) {
        String returnPage = PHOTO;
        try {
            byte[] userPhoto = storage.getPhoto();
            String encodedImage = Base64.encode(userPhoto);
            model.addAttribute("image", encodedImage);

            LOGGER.info(storage.getId() + LOG_PHOTO);

        } catch (Throwable t) {
            LOGGER.error(storage.getId(), t, t);
            returnPage = ERROR;
        }

        return returnPage;
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic, Storage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
