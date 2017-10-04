package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.CertificateWithUserForm;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import javax.validation.Valid;

import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;

@Controller
public class CreateCertificateAndUserController {

    static final String ADD_CERTIFICATE_AND_USER_JSP = "addCertificateAndUser";
    static final String CERTIFICATE_WITH_USER_FORM = "certificateWithUserForm";
    static final String MSG = "msg";

    private static final Logger LOGGER = LogManager.getLogger(CreateCertificateAndUserController.class);
    private final CertificateLogic certificateLogic;

    @PostMapping(value = "/addCertificateAndCreateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addCertificateAndCreateUser() {
        LOGGER.debug("Request to '/addCertificateAndCreateUser' " + ADD_CERTIFICATE_AND_USER_JSP);
        return new ModelAndView(ADD_CERTIFICATE_AND_USER_JSP, CERTIFICATE_WITH_USER_FORM, new CertificateWithUserForm());
    }

    @PostMapping(value = "/checkCertificateAndUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String checkCertificateAndUser(@Validated @ModelAttribute(CERTIFICATE_WITH_USER_FORM)
                                                  CertificateWithUserForm certificateWithUserForm,
                                          BindingResult bindingResult, @Valid Model model) {

        String returnPage;
        LOGGER.debug("Request to '/addCertificateAndCreateUser' ");

        if (bindingResult.hasErrors()) {
            model.addAttribute(MSG, "The data have not been validated!!!");
            returnPage = ADD_CERTIFICATE_AND_USER_JSP;
            LOGGER.warn("The data have not been validated!!!");
        } else {
            try {
                int result = certificateLogic.addCertificateAndCreateUser(certificateWithUserForm.getCertificate()
                        , certificateWithUserForm.getUser());
                model.addAttribute(MSG, "Certificate added. Certificate id = " + result);
                returnPage = ADMIN_JSP;
                LOGGER.info("Certificate added. Certificate id = " + result);
            } catch (DataIntegrityViolationException e) {
                model.addAttribute(MSG, "A person with this e-mail already exists, try again.");
                returnPage = ADD_CERTIFICATE_AND_USER_JSP;
                LOGGER.warn(e);
            } catch (Exception e) {
                returnPage = ERROR;
                LOGGER.warn(e);
            }
        }

        LOGGER.debug(String.format("Request to '/addCertificateAndCreateUser' return (%s).jsp", returnPage));
        return returnPage;
    }

    @Autowired
    public CreateCertificateAndUserController(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }
}
