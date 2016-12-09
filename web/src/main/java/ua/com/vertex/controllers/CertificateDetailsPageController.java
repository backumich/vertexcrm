package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CertificateDetailsPageController {
    private static final Logger LOGGER = LogManager.getLogger(CertificateDetailsPageController.class);

    private final CertDetailsPageLogic logic;
    private ImageStorage storage;

    private static final String PAGE_JSP = "certificateDetails";

    @RequestMapping(value = "/showCertificateDetails")
    public String doGet(HttpServletRequest request, Model model) {
        String requestedId = request.getParameter("certificationId");
        int certificationId;
        try {
            certificationId = Integer.parseInt(requestedId);
        } catch (NumberFormatException e) {
            model.addAttribute("certificateIsNull", "No such certificate! Try again!");
            return PAGE_JSP;
        }

        Certificate certificate;
        User user = null;

        LOGGER.debug("Invoking logic to get certificate ID " + certificationId);
        try {
            certificate = logic.getCertificateDetails(certificationId);
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("certificateIsNull", "No such certificate! Try again!");
            return PAGE_JSP;
        }
        model.addAttribute("certificate", certificate);

        LOGGER.debug("Invoking logic to get user ID " + certificationId);
        try {
            user = logic.getUserDetails(certificate.getUserId());
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("userIsNull", "No holder is assigned to this certificate ID");
        }
        model.addAttribute("user", user);

        model.addAttribute("result", "result");

        return PAGE_JSP;
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

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic, ImageStorage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
