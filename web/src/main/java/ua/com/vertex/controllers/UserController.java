package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.PdfDto;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.EmailExtractor;

import java.util.List;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;

@Controller
public class UserController {
    static final String CERTIFICATES = "certificates";
    static final String USER_JSP = "user";
    private static final String PDF_DTO = "dto";
    private static final String LIST_CERTIFICATE_IS_EMPTY = "listCertificatesIsEmpty";
    private static final String LOG_REQ_IN = "Request to '/getCertificateByUserId' ";
    private static final String LOG_GET_EMAIL = "Request to '/getCertificateByUserId' with userEmail=";
    private static final String LOG_REQ_OUT = "Request to '/getCertificateByUserId' return '%s.jsp' ";

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final CertificateLogic certificateLogic;
    private final EmailExtractor emailExtractor;

    @GetMapping(value = "/user")
    public ModelAndView user() {
        return new ModelAndView(USER_JSP);
    }

    @GetMapping(value = "/getCertificateByUserId")
    @PreAuthorize("isAuthenticated()")
    public String getAllCertificatesByUserEmail(Model model) {

        logger.debug(LOG_REQ_IN);
        String view;
        try {
            String email = emailExtractor.getEmailFromAuthentication();
            logger.debug(LOG_GET_EMAIL + email);
            List<Certificate> result = certificateLogic.getAllCertificatesByUserEmail(email);
            model.addAttribute(PDF_DTO, new PdfDto());
            model.addAttribute(CERTIFICATES, result);
            model.addAttribute(LIST_CERTIFICATE_IS_EMPTY, result.isEmpty());

            view = USER_JSP;
        } catch (Exception e) {
            view = ERROR;
        }

        logger.debug(String.format(LOG_REQ_OUT, view));
        return view;
    }

    @Autowired
    public UserController(CertificateLogic certificateLogic, EmailExtractor emailExtractor) {
        this.certificateLogic = certificateLogic;
        this.emailExtractor = emailExtractor;
    }
}
