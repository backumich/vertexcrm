package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.CertificateLogic;

@Controller
public class CertificateDetails {

    //todo: please use suppress warnings as rare as possible. And make variable package-private, it's ok to hide your data, this is encapsulation!!!! (like Sparta)
    @SuppressWarnings("WeakerAccess")
    public static final String CERTIFICATE_DETAIL = "certificate";

    @SuppressWarnings("WeakerAccess")
    public static final String EMPTY_RESULT = "emptyResult";

    @SuppressWarnings("WeakerAccess")
    public static final String CERTIFICATE_JSP = "certificate";

    private static final Logger LOGGER = LogManager.getLogger(CertificateDetails.class);

    private CertificateLogic certificateLogic;

    @Autowired
    public CertificateDetails(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    //todo: you can use @GetMapping
    @RequestMapping(value = "/getCertificateDetails", method = RequestMethod.GET)
    public ModelAndView getCertificateDetails(@RequestParam("certificateDetails") int certificateId) {
        LOGGER.info("Request to '/getCertificateDetails' ");
        ModelAndView result = new ModelAndView(CERTIFICATE_JSP);
        try {
            result.addObject(CERTIFICATE_DETAIL, certificateLogic.getCertificateById(certificateId));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("No certificate with the id = " + certificateId);
            result.addObject(EMPTY_RESULT, true);
        }

        //todo: if your logic would be changed and this comment wouldn't then this logging will lie to 'customers'.
        // Please craft this comment regarding request name and return value
        LOGGER.info("Request to '/getCertificateDetails' return 'certificate.jsp' ");
        return result;
    }
}
