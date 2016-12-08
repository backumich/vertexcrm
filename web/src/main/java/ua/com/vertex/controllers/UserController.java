package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.logic.UserLogic;

import java.util.List;


@Controller
public class UserController {

    @SuppressWarnings("WeakerAccess")
    public static final String CERTIFICATES = "certificates";
    @SuppressWarnings("WeakerAccess")
    public static final String LIST_CERTIFICATE_IS_EMPTY = "listCertificatesIsEmpty";
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserLogic userLogic;

    @Autowired
    public UserController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping(value = "/getCertificateByUserId", method = RequestMethod.GET)
    public String getAllCertificatesByUserId(@RequestParam("userId") int userId, Model model) {

        LOGGER.info("Request to '/getCertificateByUserId', call  - model.addAttribute()");

        List<Certificate> result = userLogic.getAllCertificatesByUserId(userId);
        model.addAttribute(CERTIFICATES, result);

        model.addAttribute(LIST_CERTIFICATE_IS_EMPTY, result.isEmpty());

        LOGGER.info("Request to '/getCertificateByUserId' return 'user.jsp' ");
        return "user";
    }


}
