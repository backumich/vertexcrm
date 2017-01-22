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

    private static final String LOG_PROCESS = "Processing request with certificateId=";
    private static final String LOG_INVALID_DATA = "Requested data is invalid";
    private static final String LOG_PASS_DATA = "Passing certificate and user data to JSP";

    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String ERROR = "error";

    @RequestMapping(value = "/certificateDetails")
    public String showCertificateDetailsPage(Model model) {
        String returnPage = CERTIFICATE_DETAILS;
        try {
            model.addAttribute("newCertificate", new Certificate());
        } catch (Throwable t) {
            LOGGER.error(logInfo.getId(), t, t);
            returnPage = ERROR;
        }
        return returnPage;
    }

    @RequestMapping(value = "/processCertificateDetails")
    public String processCertificateDetails(@Validated @ModelAttribute("newCertificate") Certificate certificate,
                                            BindingResult result, Model model) {
        String returnPage = CERTIFICATE_DETAILS;
        try {
            if (result.hasErrors()) {
                model.addAttribute("error", "Entered value must be a positive integer!");
                LOGGER.debug(logInfo.getId() + LOG_INVALID_DATA);
            } else {
                setUserAndCertificate(certificate, model);
                LOGGER.debug(logInfo.getId() + LOG_PASS_DATA);
            }
        } catch (Throwable t) {
            LOGGER.error(logInfo.getId(), t, t);
            returnPage = ERROR;
        }

        return returnPage;
    }

    private void setUserAndCertificate(Certificate certificate, Model model) {
        int certificationId = certificate.getCertificationId();

        LOGGER.debug(logInfo.getId() + LOG_PROCESS + certificationId);

        certificate = certLogic.getCertificateDetails(certificationId).orElse(EMPTY_CERTIFICATE);
        if (!EMPTY_CERTIFICATE.equals(certificate)) {
            model.addAttribute("certificate", certificate);
            User user = userLogic.getUserById(certificate.getUserId()).orElse(EMPTY_USER);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "No certificate with this ID!");
        }
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic certLogic, UserLogic userLogic, LogInfo logInfo) {
        this.certLogic = certLogic;
        this.userLogic = userLogic;
        this.logInfo = logInfo;
    }
}
