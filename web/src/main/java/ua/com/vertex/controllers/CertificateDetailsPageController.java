package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.lang.String.format;

@Controller
public class CertificateDetailsPageController {
    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);
    private final CertDetailsPageLogic logic;
    private ImageStorage storage;

    @RequestMapping(value = "/showCertificateDetails")
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String certificationId = req.getParameter("certificationId");

        clearSessionAttributes(session);

        Certificate certificate;
        User user = null;

        LOGGER.debug("Invoking logic to get certificate details");
        try {
            certificate = logic.getCertificateDetails(Integer.valueOf(certificationId));
        } catch (EmptyResultDataAccessException | NumberFormatException e) {
            session.setAttribute("certificateIsNull", "No such certificate! Try again!");
            resp.sendRedirect("/certificateDetails.jsp");
            return;
        }

        LOGGER.debug("Invoking logic to get user details");
        try {
            user = logic.getUserDetails(certificate.getUserId());
        } catch (EmptyResultDataAccessException e) {
            session.setAttribute("userIsNull", "No holder is assigned to this certificate ID");
        }

        setSessionAttributes(session, certificate, user);
        resp.sendRedirect("/certificateDetails.jsp");
    }

    @RequestMapping(value = "/showUserPhoto")
    public void showUserPhoto(HttpServletResponse resp) {
        LOGGER.debug("Transferring image to jsp");
        try {
            byte[] userPhoto = storage.getImageData();
            resp.setContentType("image/jpeg");
            resp.getOutputStream().write(userPhoto);
        } catch (IOException e) {
            LOGGER.error(e, e);
        }
    }

    private void clearSessionAttributes(HttpSession session) {
        session.removeAttribute("certificateIsNull");
        session.removeAttribute("userIsNull");
        session.removeAttribute("certificationId");
        session.removeAttribute("userFirstName");
        session.removeAttribute("userLastName");
        session.removeAttribute("userPhoto");
        session.removeAttribute("certificationDate");
        session.removeAttribute("courseName");
        session.removeAttribute("language");
    }

    private void setSessionAttributes(HttpSession session, Certificate cert, User user) {
        session.setAttribute("certificationId", format("Certification ID: %05d", cert.getCertificationId()));
        session.setAttribute("userFirstName", format("User First Name: %s",
                user == null ? "-" : user.getFirstName()));
        session.setAttribute("userLastName", format("User Last Name: %s",
                user == null ? "-" : user.getLastName()));
        session.setAttribute("certificationDate", format("Certification Date: %s", cert.getCertificationDate()));
        session.setAttribute("courseName", format("Course Name: %s", cert.getCourseName()));
        session.setAttribute("language", format("Programming Language: %s", cert.getLanguage()));
        if (user != null) {
            session.setAttribute("userPhoto", user.getPhoto() == null ? "" : "notNull");
        }
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic, ImageStorage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
