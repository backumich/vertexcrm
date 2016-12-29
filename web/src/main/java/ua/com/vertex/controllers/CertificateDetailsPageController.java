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

    private static final String PAGE_JSP = "certificateDetails";
    private static final String ERROR_JSP = "error";
    private static final String PHOTO_JSP = "certificateHolderPhoto";

    @RequestMapping(value = "/" + PAGE_JSP)
    public String showCertificateDetailsPage(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        storage.setSessionId("[Session Id: " + session.getId() + "] ");
        String returnPage = PAGE_JSP;
//        boolean errorJsp = false;

        try {
            model.addAttribute("newCertificate", new Certificate());
        } catch (Throwable t) {
            LOGGER.error(storage.getSessionId(), t, t);
            returnPage = ERROR_JSP;
        }
//        return errorJsp ? ERROR_JSP : PAGE_JSP;
        return returnPage;
    }

    @RequestMapping(value = "/processCertificateDetails")
    public String processCertificateDetails(@Validated @ModelAttribute("certificate") Certificate certificate,
                                            BindingResult result, Model model) {
//        boolean bindingResult = false;
//        boolean error = false;
        String returnPage = PAGE_JSP;
        try {
            if (result.hasErrors()) {
                model.addAttribute("error", "Entered value must be a positive integer");
                LOGGER.info(storage.getSessionId() + LOG_INVALID_DATA);
//                bindingResult = true;
            } else {
//todo: else-block works here for sure!
//            if (!bindingResult) {
                int certificationId = certificate.getCertificationId();

                LOGGER.info(storage.getSessionId() + LOG_PROCESS + certificationId);

                certificate = getCertificateDetails(certificationId);
                if (!Certificate.BAD_SERTIFICATE.equals(certificate)) {
                    model.addAttribute("certificate", certificate);

                    User user = logic.getUserDetails(certificate.getUserId()).orElse(new User());
                    model.addAttribute("user", user);
                    //todo: what is the effort of storage if you still put the photo into model (as user's field) and sending it to requester?
                    storage.setPhoto(user.getPhoto());
                } else {
                    model.addAttribute("error", "No certificate with this ID! Try again!");
                }

                LOGGER.info(storage.getSessionId() + LOG_PASS_DATA);
            }
        } catch (Exception t) {
            LOGGER.error(storage.getSessionId(), t, t);
//            error = true;
            returnPage = ERROR_JSP;
        }

//        return error ? ERROR_JSP : PAGE_JSP;
        return returnPage;
    }

    //todo: this method was like swiss knife, was making all actions you could wish! it brakes single responsibility principle
    private Certificate getCertificateDetails(int certificationId) {
        return logic.getCertificateDetails(certificationId).orElse(Certificate.BAD_SERTIFICATE);
    }

    @RequestMapping(value = "/certificateHolderPhoto")
    public String showUserPhoto(Model model) {
        boolean error = false;
        try {
            byte[] userPhoto = storage.getPhoto();
            String encodedImage = Base64.encode(userPhoto);
            model.addAttribute("image", encodedImage);
            LOGGER.info(storage.getSessionId() + LOG_PHOTO);
        } catch (Throwable t) {
            LOGGER.error(storage.getSessionId(), t, t);
            error = true;
        }

        return error ? ERROR_JSP : PHOTO_JSP;
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic, Storage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
