package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CertificateDetailsPageController {
    private final CertDetailsPageLogic logic;
    private final ImageStorage storage;

    private static final String PAGE_JSP = "certificateDetails";

    @RequestMapping(value = "/" + PAGE_JSP)
    public String showCertificateDetailsPage(){
        return PAGE_JSP;
    }

    @RequestMapping(value = "/processCertificateDetails")
    public String processCertificateDetails(@RequestParam("certificationId") String requestedId, Model model) {
        int certificationId;
        Certificate certificate;
        User user = new User();

        try {
            certificationId = Integer.parseInt(requestedId);
        } catch (NumberFormatException | EmptyResultDataAccessException e) {
            model.addAttribute("error", "Wrong entry! Enter an integer value!");
            return PAGE_JSP;
        }

        certificate = logic.getCertificateDetails(certificationId);
        if (certificate.getCertificationId() != 0) {
            model.addAttribute("certificate", certificate);
            user = logic.getUserDetails(certificate.getUserId());
        } else {
            model.addAttribute("error", "No certificate with this ID! Try again!");
        }

        if (user.getUserId() != 0) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "No holder is assigned to this certificate ID!");
        }

        return PAGE_JSP;
    }

    @RequestMapping(value = "/showUserPhoto")
    public void showUserPhoto(HttpServletResponse resp) {
        try {
            byte[] userPhoto = storage.getImageData();
            resp.setContentType("image/jpeg");
            resp.getOutputStream().write(userPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public CertificateDetailsPageController(CertDetailsPageLogic logic, ImageStorage storage) {
        this.logic = logic;
        this.storage = storage;
    }
}
