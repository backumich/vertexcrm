package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CertificateDetailsPageController {
    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);
    private final CertDetailsPageLogic logic;

    @RequestMapping(value = "/showCertificateDetails")
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String certificationId = req.getParameter("certificationId");

        LOGGER.debug("Controller invokes logic");
        try {
            logic.getCertificateDetails(session, certificationId);
            resp.sendRedirect("/certificateDetails.jsp");
        } catch (IOException e) {
            LOGGER.error(e, e);
        }
    }

    @RequestMapping(value = "/showUserPhoto")
    public void showUserPhoto(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String userId = session.getAttribute("userId").toString();

        LOGGER.debug("Controller invokes logic");
        try {
            byte[] userPhoto = logic.getUserPhoto(userId);
            resp.setContentType("image/jpeg");
            resp.getOutputStream().write(userPhoto);
        } catch (IOException e) {
            LOGGER.error(e, e);
        }
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic) {
        this.logic = logic;
    }
}
