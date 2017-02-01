package ua.com.vertex.controllers;

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
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;

import static ua.com.vertex.beans.Certificate.EMPTY_CERTIFICATE;
import static ua.com.vertex.beans.User.EMPTY_USER;

@Controller
public class CertificateDetailsPageController {

    private final CertDetailsPageLogic certLogic;
    private final UserLogic userLogic;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);

    private static final String USER = "user";
    private static final String CERTIFICATE = "certificate";
    private static final String NEW_CERTIFICATE = "newCertificate";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String ERROR = "error";

    @RequestMapping(value = "/certificateDetails")
    public String showCertificateDetailsPage(Model model) {
        String returnPage = CERTIFICATE_DETAILS;
        try {
            model.addAttribute(NEW_CERTIFICATE, new Certificate());
        } catch (Throwable t) {
            LOGGER.error(logInfo.getId(), t, t);
            returnPage = ERROR;
        }
        return returnPage;
    }

    @RequestMapping(value = "/processCertificateDetails")
    public String processCertificateDetails(@Validated @ModelAttribute(NEW_CERTIFICATE) Certificate certificate,
                                            BindingResult result, Model model) {

        String returnPage = CERTIFICATE_DETAILS;
        try {
            if (result.hasErrors()) {
                model.addAttribute(ERROR, "Entered value must be a positive integer!");
                LOGGER.debug(logInfo.getId() + "Requested data are invalid");
            } else {
                setUserAndCertificate(certificate, model);
                LOGGER.debug(logInfo.getId() + "Passing certificate and user data to JSP");
            }
        } catch (Throwable t) {
            LOGGER.error(logInfo.getId(), t, t);
            returnPage = ERROR;
        }

        return returnPage;
    }

    private void setUserAndCertificate(Certificate certificate, Model model) {
        int certificationId = certificate.getCertificationId();

        LOGGER.debug(logInfo.getId() + "Processing request with certificateId=" + certificationId);

        certificate = certLogic.getCertificateDetails(certificationId).orElse(EMPTY_CERTIFICATE);
        if (!EMPTY_CERTIFICATE.equals(certificate)) {
            model.addAttribute(CERTIFICATE, certificate);
            User user = userLogic.getUserById(certificate.getUserId()).orElse(EMPTY_USER);
            model.addAttribute(USER, user);
        } else {
            model.addAttribute(ERROR, "No certificate with this ID!");
        }
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic certLogic, UserLogic userLogic, LogInfo logInfo) {
        this.certLogic = certLogic;
        this.userLogic = userLogic;
        this.logInfo = logInfo;
    }
}
