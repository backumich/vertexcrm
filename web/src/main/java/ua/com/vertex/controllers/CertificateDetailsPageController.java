package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.LogInfo;

@Controller
public class CertificateDetailsPageController {

    private final CertificateLogic certLogic;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);

    private static final String CERTIFICATE_DETAILS = "certificateDetails";
    static final String ERROR = "error";

    @RequestMapping(value = "/certificateDetails")
    public String showCertificateDetailsPage() {
        return CERTIFICATE_DETAILS;
    }

    @RequestMapping(value = "/getCertificate")
    public String getCertificate(@RequestParam String certificateUid, Model model) {
        return process(certificateUid, model);
    }

    @RequestMapping(value = "/getCertificate/{certificateUid}")
    public String getCertificateByCertificateUid(@PathVariable String certificateUid, Model model) {
        return process(certificateUid, model);
    }

    private String process(String certificateUid, Model model) {
        String view = CERTIFICATE_DETAILS;

        certificateUid = certificateUid.replaceAll("-", "");
        try {
            certLogic.getUserAndCertificate(certificateUid, model);
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e);
            view = ERROR;
        }

        return view;
    }

    @Autowired
    public CertificateDetailsPageController(CertificateLogic certLogic, LogInfo logInfo) {
        this.certLogic = certLogic;
        this.logInfo = logInfo;
    }
}
