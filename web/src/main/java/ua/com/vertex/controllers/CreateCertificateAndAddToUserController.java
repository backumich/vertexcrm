package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;

import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@Controller
public class CreateCertificateAndAddToUserController {

    static final String SELECT_USER_JSP = "selectUser";
    static final String ADD_CERTIFICATE_WITH_USER_ID_JSP = "addCertificateWithUserId";
    static final String USER_ID = "userIdForCertificate";
    static final String CERTIFICATE = "certificate";
    static final String USERS = "users";
    private final String USER_DATA = "userDataForSearch";

    private static final Logger LOGGER = LogManager.getLogger(CreateCertificateAndAddToUserController.class);

    private final CertificateLogic certificateLogic;
    private final UserLogic userLogic;

    @Autowired
    public CreateCertificateAndAddToUserController(CertificateLogic certificateLogic, UserLogic userLogic) {
        this.certificateLogic = certificateLogic;
        this.userLogic = userLogic;
    }

    @PostMapping(value = "/addCertificateWithUserId")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addCertificateWithUserId() {
        LOGGER.debug("Request to '/addCertificateWithUserId' . Redirect to " + SELECT_USER_JSP);
        return new ModelAndView(SELECT_USER_JSP);
    }

    @PostMapping(value = "/searchUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchUser(@ModelAttribute(USER_DATA) String userData, Model model) {

        LOGGER.debug(String.format("Call - userLogic.searchUser(%s);", userData));

        if (userData.isEmpty()) {
            model.addAttribute(MSG, "The data have not been validated!!!");
            LOGGER.info(String.format("Call - userLogic.searchUser(%s);", userData) + "The data have not been validated!!!");
        } else {
            List<User> users = userLogic.searchUser(userData);
            model.addAttribute(USERS, users);
            LOGGER.debug(String.format("Call - userLogic.searchUser(%s);", userData));
            if (users.isEmpty()) {
                model.addAttribute(MSG, "User not found, try again.");
                LOGGER.debug("User not found, try again.");
            }
        }

        LOGGER.debug("Request to '/selectUser' redirect to page - " + SELECT_USER_JSP);
        return SELECT_USER_JSP;
    }

    @PostMapping(value = "/selectUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView selectUser(@RequestParam(USER_ID) int userId) {
        LOGGER.debug(String.format("Request to '/selectUser' with user id = (%s). Redirect to - %s.jsp", userId,
                SELECT_USER_JSP));
        ModelAndView result = new ModelAndView(ADD_CERTIFICATE_WITH_USER_ID_JSP, CERTIFICATE, new Certificate());
        result.addObject(USER_ID, userId);
        return result;
    }

    @PostMapping(value = "/checkCertificateWithUserId")
    @PreAuthorize("hasRole('ADMIN')")
    public String checkCertificateWithUserId(@Validated @ModelAttribute(CERTIFICATE) Certificate certificate,
                                             BindingResult bindingResult, Model model) {

        String returnPage = ADD_CERTIFICATE_WITH_USER_ID_JSP;
        LOGGER.debug("Request to '/addCertificateWithUserId' ");

        if (bindingResult.hasErrors()) {
            model.addAttribute(MSG, "The data have not been validated!!!");
            LOGGER.warn("The data have not been validated!!!");
        } else {
            int result = certificateLogic.addCertificate(certificate);
            model.addAttribute(MSG, "Certificate added. Certificate id = " + result);
            returnPage = ADMIN_JSP;
            LOGGER.info("Certificate added. Certificate id = " + result);
        }

        LOGGER.debug(String.format("Request to '/addCertificateWithUserId' return (%s).jsp", returnPage));
        return returnPage;
    }
}
