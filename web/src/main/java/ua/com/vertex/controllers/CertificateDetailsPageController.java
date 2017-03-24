package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.utils.LogInfo;

@Controller
public class CertificateDetailsPageController {

    private final CertDetailsPageLogic certLogic;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);

    private static final int WRONG_ID = -1;
    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    static final String ERROR = "error";

    @RequestMapping(value = "/certificateDetails")
    public String showCertificateDetailsPage() {
        return CERTIFICATE_DETAILS;
    }

    @RequestMapping(value = "/getCertificate")
    public String getCertificate(@RequestParam String certificateIdEncoded, Model model) {

        String view = CERTIFICATE_DETAILS;

        int certificateId = certLogic.decodeId(certificateIdEncoded, model);
        if (certificateId == WRONG_ID) {
            model.addAttribute(ERROR, "Invalid entered data");
            LOGGER.debug(logInfo.getId() + "Entered certificateId data are invalid");
        } else {
            try {
                certLogic.setUserAndCertificate(certificateId, model);
                LOGGER.debug(logInfo.getId() + "Passing certificate and user data to JSP");
            } catch (Exception e) {
                LOGGER.warn(logInfo.getId(), e, e);
                view = ERROR;
            }
        }

        return view;
    }

    @RequestMapping(value = "/getCertificate/{certificateIdEncoded}")
    public String getCertificateByCertificateId(@PathVariable String certificateIdEncoded, Model model) {

        String view = CERTIFICATE_DETAILS;

        int certificateId = certLogic.decodeId(certificateIdEncoded, model);
        if (certificateId == WRONG_ID) {
            model.addAttribute(ERROR, "Invalid entered data");
            LOGGER.debug(logInfo.getId() + "Entered certificateId data are invalid");
        } else {
            try {
                certLogic.setUserAndCertificate(certificateId, model);
            } catch (Exception e) {
                LOGGER.warn(logInfo.getId(), e, e);
                view = ERROR;
            }
        }

        return view;
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic certLogic, LogInfo logInfo) {
        this.certLogic = certLogic;
        this.logInfo = logInfo;
    }
}
