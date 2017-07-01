package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.PdfDto;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.LogInfo;

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

    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final CertificateLogic certificateLogic;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView user() {
        return new ModelAndView(USER_JSP);
    }

    @RequestMapping(value = "/getCertificateByUserId", method = RequestMethod.GET)
    public String getAllCertificatesByUserEmail(Model model) {

        LOGGER.debug(LOG_REQ_IN);
        String view;
        try {
            String eMail = logInfo.getEmail();
            LOGGER.debug(LOG_GET_EMAIL + eMail);
            List<Certificate> result = certificateLogic.getAllCertificatesByUserEmail(eMail);
            model.addAttribute(PDF_DTO, new PdfDto());
            model.addAttribute(CERTIFICATES, result);
            model.addAttribute(LIST_CERTIFICATE_IS_EMPTY, result.isEmpty());

            view = USER_JSP;
        } catch (Exception e) {
            view = ERROR;
        }

        LOGGER.debug(String.format(LOG_REQ_OUT, view));
        return view;
    }

    @Autowired
    public UserController(LogInfo logInfo, CertificateLogic certificateLogic) {
        this.logInfo = logInfo;
        this.certificateLogic = certificateLogic;
    }

}
