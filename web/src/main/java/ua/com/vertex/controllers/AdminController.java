package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.CertificateWithUserForm;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;
import java.util.List;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;

@Controller
public class AdminController {

    static final String ADMIN_JSP = "admin";
    static final String SELECT_USER_JSP = "selectUser";
    static final String ADD_CERTIFICATE_AND_USER_JSP = "addCertificateAndUser";
    static final String ADD_CERTIFICATE_WITH_USER_ID_JSP = "addCertificateWithUserId";
    static final String USER_ID = "userIdForCertificate";
    static final String MSG = "msg";
    static final String CERTIFICATE = "certificate";
    static final String USERS = "users";
    static final String CERTIFICATE_WITH_USER_FORM = "certificateWithUserForm";
    static final String LOG_CERTIFICATE_ADDED = "Certificate added. Certificate id = ";
    static final String LOG_INCORRECT_DATA = "The data have not been validated!!!";
    static final String LOG_USER_NOT_FOUND = "User not found, try again.";
    static final String LOG_INVALID_USER_EMAIL = "A person with this e-mail already exists, try again.";
    private final String USER_DATA = "userDataForSearch";
    private final String LOG_REQ_ADD_CERTIFICATE_AND_CREATE_USER = "Request to '/addCertificateAndCreateUser' ";
    private final String LOG_REQ_SELECT_USER = "Request to '/selectUser' with user id = (%s). Redirect to ";

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
    private final CertificateLogic certificateLogic;
    private final UserLogic userLogic;

    @GetMapping(value = "/admin")
    public ModelAndView admin() {
        LOGGER.debug("Request to '/admin' redirect to page - " + ADMIN_JSP);
        return new ModelAndView(ADMIN_JSP);
    }

    @PostMapping(value = "/addCertificateWithUserId")
    public ModelAndView addCertificateWithUserId() {
        LOGGER.debug(LOG_REQ_SELECT_USER + SELECT_USER_JSP);
        return new ModelAndView(SELECT_USER_JSP);
    }

    @PostMapping(value = "/searchUser")
    public String searchUser(@ModelAttribute(USER_DATA) String userData, Model model) {

        LOGGER.debug(String.format("Call - userLogic.searchUser(%s);", userData));

        String result;

        if (userData.isEmpty()) {
            model.addAttribute(MSG, LOG_INCORRECT_DATA);
            result = SELECT_USER_JSP;
            LOGGER.info(String.format("Call - userLogic.searchUser(%s);", userData) + LOG_INCORRECT_DATA);
        } else {
            try {
                List<User> users = userLogic.searchUser(userData);
                model.addAttribute(USERS, users);
                LOGGER.debug(String.format("Call - userLogic.searchUser(%s);", userData));
                if (users.isEmpty()) {
                    model.addAttribute(MSG, LOG_USER_NOT_FOUND);
                    LOGGER.debug(LOG_USER_NOT_FOUND);
                }
                result = SELECT_USER_JSP;
            } catch (Exception e) {
                result = ERROR;
                LOGGER.warn(e);
            }
        }

        LOGGER.debug("Request to '/selectUser' redirect to page - " + result);
        return result;
    }

    @PostMapping(value = "/selectUser")
    public ModelAndView selectUser(@ModelAttribute(USER_ID) int userId) {
        LOGGER.debug(String.format(LOG_REQ_SELECT_USER, userId) + SELECT_USER_JSP);
        ModelAndView result = new ModelAndView(ADD_CERTIFICATE_WITH_USER_ID_JSP, CERTIFICATE, new Certificate());
        result.addObject(USER_ID, userId);
        return result;
    }

    @PostMapping(value = "/checkCertificateWithUserId")
    public String checkCertificateWithUserId(@Validated @ModelAttribute(CERTIFICATE) Certificate certificate,
                                             BindingResult bindingResult, Model model) {

        String returnPage;
        LOGGER.debug("Request to '/addCertificateWithUserId' ");

        if (bindingResult.hasErrors()) {
            model.addAttribute(MSG, LOG_INCORRECT_DATA);
            returnPage = ADD_CERTIFICATE_WITH_USER_ID_JSP;
            LOGGER.warn(LOG_INCORRECT_DATA);
        } else {
            try {
                int result = certificateLogic.addCertificate(certificate);
                model.addAttribute(MSG, LOG_CERTIFICATE_ADDED + result);
                returnPage = ADMIN_JSP;
                LOGGER.info(LOG_CERTIFICATE_ADDED + result);
            } catch (Exception e) {
                returnPage = ERROR;
                LOGGER.warn(e);
            }
        }

        LOGGER.debug(String.format("Request to '/addCertificateWithUserId' return (%s).jsp", returnPage));
        return returnPage;
    }

    @PostMapping(value = "/addCertificateAndCreateUser")
    public ModelAndView addCertificateAndCreateUser() {
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE_AND_CREATE_USER + ADD_CERTIFICATE_AND_USER_JSP);
        return new ModelAndView(ADD_CERTIFICATE_AND_USER_JSP, CERTIFICATE_WITH_USER_FORM, new CertificateWithUserForm());
    }

    @PostMapping(value = "/checkCertificateAndUser")
    public String checkCertificateAndUser(@Validated @ModelAttribute(CERTIFICATE_WITH_USER_FORM) CertificateWithUserForm certificateWithUserForm,
                                          BindingResult bindingResult, @Valid Model model) {

        String returnPage;
        LOGGER.debug(LOG_REQ_ADD_CERTIFICATE_AND_CREATE_USER);

        if (bindingResult.hasErrors()) {
            model.addAttribute(MSG, LOG_INCORRECT_DATA);
            returnPage = ADD_CERTIFICATE_AND_USER_JSP;
            LOGGER.warn(LOG_INCORRECT_DATA);
        } else {
            try {
                int result = certificateLogic.addCertificateAndCreateUser(certificateWithUserForm.getCertificate()
                        , certificateWithUserForm.getUser());
                model.addAttribute(MSG, LOG_CERTIFICATE_ADDED + result);
                returnPage = ADMIN_JSP;
                LOGGER.info(LOG_CERTIFICATE_ADDED + result);
            } catch (DataIntegrityViolationException e) {
                model.addAttribute(MSG, LOG_INVALID_USER_EMAIL);
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
    public AdminController(CertificateLogic certificateLogic, UserLogic userLogic) {
        this.certificateLogic = certificateLogic;
        this.userLogic = userLogic;
    }

}
