package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.LogInfo;
import ua.com.vertex.utils.TransformId;

import static ua.com.vertex.beans.Certificate.EMPTY_CERTIFICATE;
import static ua.com.vertex.beans.User.EMPTY_USER;

@Controller
public class CertificateDetailsPageController {

    private final CertDetailsPageLogic certLogic;
    private final UserLogic userLogic;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);

    private static final int WRONG_ID = -1;
    private static final String USER = "user";
    private static final String CERTIFICATE = "certificate";
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    private static final String CERTIFICATE_LINK = "certificateLink";
    private static final String ERROR = "error";

    @RequestMapping(value = "/certificateDetails")
    public String showCertificateDetailsPage() {
        return CERTIFICATE_DETAILS;
    }

    @RequestMapping(value = "/getCertificate")
    public String getCertificate(@RequestParam String certificateIdEncoded, Model model) {

        String view = CERTIFICATE_DETAILS;

        int certificateId = decodeId(certificateIdEncoded, model);
        if (certificateId == WRONG_ID) {
            model.addAttribute(ERROR, "Invalid entered data");
            LOGGER.debug(logInfo.getId() + "Entered certificateId data are invalid");
        } else {
            try {
                setUserAndCertificate(certificateId, model);
                LOGGER.debug(logInfo.getId() + "Passing certificate and user data to JSP");
            } catch (Exception e) {
                LOGGER.warn(logInfo.getId(), e, e);
                view = ERROR;
            }
        }

        return view;
    }

    private int decodeId(String certificateIdEncoded, Model model) {
        int certificateId = WRONG_ID;
        try {
            certificateId = TransformId.decode(certificateIdEncoded);
            model.addAttribute(CERTIFICATE_LINK, certificateIdEncoded);
        } catch (Exception e2) {
            LOGGER.warn(logInfo.getId(), e2, e2);
        }

        return certificateId;
    }

    private void setUserAndCertificate(int certificationId, Model model) {
        LOGGER.debug(logInfo.getId() + "Processing request with certificateId=" + certificationId);

        Certificate certificate = certLogic.getCertificateDetails(certificationId).orElse(EMPTY_CERTIFICATE);
        if (!EMPTY_CERTIFICATE.equals(certificate)) {
            model.addAttribute(CERTIFICATE, certificate);
            User user = userLogic.getUserById(certificate.getUserId()).orElse(EMPTY_USER);
            model.addAttribute(USER, user);
        } else {
            model.addAttribute(ERROR, "No certificate with this ID!");
        }
    }

    @RequestMapping(value = "/getCertificate/{certificateIdEncoded}")
    public String getCertificateByCertificateId(@PathVariable String certificateIdEncoded, Model model) {

        String view = CERTIFICATE_DETAILS;

        int certificateId = decodeId(certificateIdEncoded, model);
        if (certificateId == WRONG_ID) {
            model.addAttribute(ERROR, "Invalid entered data");
            LOGGER.debug(logInfo.getId() + "Entered certificateId data are invalid");
        } else {
            try {
                setUserAndCertificate(certificateId, model);
            } catch (Exception e) {
                LOGGER.warn(logInfo.getId(), e, e);
                view = ERROR;
            }
        }

        return view;
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic certLogic, UserLogic userLogic, LogInfo logInfo) {
        this.certLogic = certLogic;
        this.userLogic = userLogic;
        this.logInfo = logInfo;
    }
}
