package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.beans.PropertyEditorSupport;


@Controller
public class AdminController {

    private static final String ADD_CERTIFICATE_JSP = "addCertificate";
    private static final String ADMIN_JSP = "admin";
    private static final String CERTIFICATE = "certificate";
    private static final String MSG = "msg";

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    private static final String LOG_REQ_ADD_CERTIFICATE = "Request to '/addCertificate' redirect to page - ";
    private static final String LOG_CERTIFICATE_INCORECT_DATA = "The data have not been validated!!!";
    private static final String LOG_CERTIFICATE_ADDED = "Certificate added. Certificate id=";

    private final CertificateLogic certificateLogic;

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
            LOGGER.warn(LOG_CERTIFICATE_INCORECT_DATA);
            model.addAttribute(MSG, LOG_CERTIFICATE_INCORECT_DATA);
        } else {
            int result = certificateLogic.addCertificate(certificate);
            model.addAttribute(MSG, LOG_CERTIFICATE_ADDED + result);
            LOGGER.info(LOG_CERTIFICATE_ADDED);
        }

        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + ADD_CERTIFICATE_JSP);
        return ADD_CERTIFICATE_JSP;
    }

    @InitBinder(CERTIFICATE)
    public void customizeBinding(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null) {
                    return;
                }
                setValue(text.replaceAll("<.[^<>]*?>", ""));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return (value != null ? value.toString() : "");
            }
        });
    }

    @Autowired
    public AdminController(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }
}
