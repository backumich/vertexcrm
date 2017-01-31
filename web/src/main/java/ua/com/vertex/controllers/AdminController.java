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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.interfaces.CertificateLogic;

@Controller
public class AdminController {

    static final String ADD_CERTIFICATE_JSP = "addCertificate";
    static final String ADMIN_JSP = "admin";
    static final String MSG = "msg";
    private static final String CERTIFICATE = "certificate";
    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    private static final String LOG_REQ_ADD_CERTIFICATE = "Request to '/addCertificate' redirect to page - ";
    private static final String LOG_CERTIFICATE_INCORRECT_DATA = "The data have not been validated!!!";
    private static final String LOG_CERTIFICATE_ADDED = "Certificate added. Certificate id=";

    private final CertificateLogic certificateLogic;

    @Autowired
    public AdminController(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        return new ModelAndView(ADMIN_JSP);
    }

    @RequestMapping(value = "/addCertificate", method = RequestMethod.POST)
    public ModelAndView addCertificate() {
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + ADD_CERTIFICATE_JSP);
        return new ModelAndView(ADD_CERTIFICATE_JSP, CERTIFICATE, new Certificate());
    }

    @RequestMapping(value = "/checkCertificate", method = RequestMethod.POST)
    public String checkCertificate(@Validated @ModelAttribute(CERTIFICATE) Certificate certificate,
                                   BindingResult bindingResult, Model model) {

        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE);

        if (bindingResult.hasErrors()) {
            LOGGER.warn(LOG_CERTIFICATE_INCORRECT_DATA);
            model.addAttribute(MSG, LOG_CERTIFICATE_INCORRECT_DATA);
        } else {
            int result = certificateLogic.addCertificate(certificate);
            model.addAttribute(MSG, LOG_CERTIFICATE_ADDED + result);
            LOGGER.info(LOG_CERTIFICATE_ADDED);
        }

        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + ADD_CERTIFICATE_JSP);
        return ADD_CERTIFICATE_JSP;
    }
}
