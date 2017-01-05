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
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.util.List;


@Controller
public class UserController {


    static final String CERTIFICATES = "certificates";
    private static final String USER_JSP = "user";
    private static final String LIST_CERTIFICATE_IS_EMPTY = "listCertificatesIsEmpty";


    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final CertificateLogic certificateLogic;

    @Autowired
    public UserController(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    @RequestMapping(value = "/getCertificateByUserId", method = RequestMethod.GET)
    public String getAllCertificatesByUserId(@RequestParam("userId") int userId, Model model) {

        LOGGER.info("Request to '/getCertificateByUserId' with userId = " + userId);

        List<Certificate> result = certificateLogic.getAllCertificatesByUserId(userId);
        model.addAttribute(CERTIFICATES, result);

        model.addAttribute(LIST_CERTIFICATE_IS_EMPTY, result.isEmpty());

        LOGGER.info("Request to '/getCertificateByUserId' return 'user.jsp' ");

        return USER_JSP;
    }


}
