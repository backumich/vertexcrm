package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.CertificateWithUserForm;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR_JSP;

@Controller
public class AdminController {

    static final String ADD_CERTIFICATE_AND_USER_JSP = "addCertificateAndUser";
    static final String ADD_CERTIFICATE_WITH_USER_ID_JSP = "addCertificateWithUserId";
    static final String ADMIN_JSP = "admin";
    static final String MSG = "msg";
    private static final String CERTIFICATE = "certificate";
    private static final String CERTIFICATE_WITH_USER_FORM = "certificateWithUserForm";
    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    private static final String LOG_REQ_ADD_CERTIFICATE = "Request to '/addCertificate' redirect to page - ";
    private static final String LOG_INCORRECT_DATA = "The data have not been validated!!!";
    static final String LOG_CERTIFICATE_ADDED = "Certificate added. Certificate id=";
    static final String LOG_INVALID_USER_ID = "Invalid user id, try again.";
    static final String LOG_INVALID_USER_EMAIL = "A person with this e-mail already exists, try again.";
    static final String LOG_EXEPTION = "Access denied or other exception.";

    private final CertificateLogic certificateLogic;
    private final UserLogic userLogic;

    @Autowired
    public AdminController(CertificateLogic certificateLogic, UserLogic userLogic) {
        this.certificateLogic = certificateLogic;
        this.userLogic = userLogic;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        return new ModelAndView(ADMIN_JSP);
    }

    @RequestMapping(value = "/addCertificateAndCreateUser", method = RequestMethod.POST)
    public ModelAndView addCertificateAndCreateUser() {
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + ADD_CERTIFICATE_AND_USER_JSP);
        return new ModelAndView(ADD_CERTIFICATE_AND_USER_JSP, CERTIFICATE_WITH_USER_FORM, new CertificateWithUserForm());
    }

    @RequestMapping(value = "/addCertificateWithUserId", method = RequestMethod.POST)
    public ModelAndView addCertificateWithUserId() {
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + ADD_CERTIFICATE_WITH_USER_ID_JSP);
        return new ModelAndView(ADD_CERTIFICATE_WITH_USER_ID_JSP, CERTIFICATE, new Certificate());
    }

    @RequestMapping(value = "/checkCertificateWithUserId", method = RequestMethod.POST)
    public String checkCertificateWithUserId(@Validated @ModelAttribute(CERTIFICATE) Certificate certificate,
                                             BindingResult bindingResult, Model model) {

        String returnPage;
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE);

        if (bindingResult.hasErrors()) {
            model.addAttribute(MSG, LOG_INCORRECT_DATA);
            returnPage = ADD_CERTIFICATE_WITH_USER_ID_JSP;
            LOGGER.warn(LOG_INCORRECT_DATA);
        } else {
            try {
                int result = certificateLogic.addCertificate(certificate);
                model.addAttribute(MSG, LOG_CERTIFICATE_ADDED + result);
                returnPage = ADMIN_JSP;
                LOGGER.info(LOG_CERTIFICATE_ADDED);
            } catch (DataIntegrityViolationException e) {
                model.addAttribute(MSG, LOG_INVALID_USER_ID);
                returnPage = ADD_CERTIFICATE_WITH_USER_ID_JSP;
                LOGGER.warn(LOG_INVALID_USER_ID);
            } catch (Exception e) {
                returnPage = ERROR_JSP;
                LOGGER.warn(LOG_EXEPTION);
            }
        }

        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + returnPage);
        return returnPage;
    }

    @RequestMapping(value = "/checkCertificateAndUser", method = RequestMethod.POST)
    public String checkCertificateAndUser(@Validated @ModelAttribute(CERTIFICATE_WITH_USER_FORM) CertificateWithUserForm certificateWithUserForm,
                                          BindingResult bindingResult, @Valid Model model) {

        String returnPage;
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE);

        if (bindingResult.hasErrors()) {
            model.addAttribute(MSG, LOG_INCORRECT_DATA);
            returnPage = ADD_CERTIFICATE_AND_USER_JSP;
            LOGGER.warn(LOG_INCORRECT_DATA);
        } else {
            try {
                int result = certificateLogic.addCertificateAndCreateUser(certificateWithUserForm.getCertificate()
                        , certificateWithUserForm.getUser());
                returnPage = ADMIN_JSP;
                LOGGER.info(LOG_CERTIFICATE_ADDED + result);
            } catch (DataIntegrityViolationException e) {
                model.addAttribute(MSG, LOG_INVALID_USER_EMAIL);
                returnPage = ADD_CERTIFICATE_AND_USER_JSP;
                LOGGER.warn(LOG_INVALID_USER_EMAIL);
            } catch (Exception e) {
                returnPage = ERROR_JSP;
                LOGGER.warn(LOG_EXEPTION);
            }
        }

        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE + returnPage);
        return returnPage;
    }

}
