package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR_JSP;

@Controller
public class CertificateDetails {

    static final String CERTIFICATE_DETAIL = "certificate";

    static final String CERTIFICATE_JSP = "certificate";

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetails.class);
    private static final String LOG_REQ_IN = "Request to '/getCertificateDetails' with certificateId=";
    private static final String LOG_REQ_OUT = "Request to '/getCertificateDetails' return ";


    private CertificateLogic certificateLogic;

    @Autowired
    public CertificateDetails(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }


    @GetMapping(value = "/getCertificateDetails")
    public ModelAndView getCertificateDetails(@RequestParam("certificateDetails") int certificateId) {

        LOGGER.info(LOG_REQ_IN + certificateId);

        ModelAndView result = new ModelAndView();

        try {

//            result.addObject(CERTIFICATE_DETAIL,certificateLogic.getCertificateById(certificateId).isPresent()
//                    ? certificateLogic.getCertificateById(certificateId).get() : null );

            if (certificateLogic.getCertificateById(certificateId).isPresent()) {

                result.addObject(CERTIFICATE_DETAIL, certificateLogic.getCertificateById(certificateId).get());
            } else {
                result.addObject(CERTIFICATE_DETAIL, null);
            }
            result.setViewName(CERTIFICATE_JSP);
        } catch (Exception e) {
            LOGGER.warn(e);
            result.setViewName(ERROR_JSP);
        }


        LOGGER.info(LOG_REQ_OUT + result.getViewName());
        return result;
    }
}
