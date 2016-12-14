package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.CertificateLogic;

@Controller
public class CertificateDetails {

    private static final String CERTIFICATE_DETAIL = "certificate";

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetails.class);

    private CertificateLogic certificateLogic;

    @Autowired
    public CertificateDetails(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    @RequestMapping(value = "/getCertificateDetails", method = RequestMethod.GET)
    public ModelAndView certificateDetails(@RequestParam("certificateDetails") int certificateId) {
        LOGGER.info("Request to '/getCertificateDetails' ");
        ModelAndView result = new ModelAndView();
        result.setViewName("certificate");
        result.addObject(CERTIFICATE_DETAIL, certificateLogic.getCertificateById(certificateId));
        LOGGER.info("Request to '/getCertificateDetails' return 'certificate.jsp' ");
        return result;
    }
}
