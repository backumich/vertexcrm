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

    @RequestMapping(value = "/certificateDetails")
    public String goToCertificateDetails() {
        return PAGE_JSP;
    }

    @RequestMapping(value = "/showCertificateDetails")
    public String showCertificateDetails(@RequestParam("certificationId") String requestedId, Model model) {
        int certificationId;
        Certificate certificate;
        User user = null;

        try {
            certificationId = Integer.parseInt(requestedId);
            certificate = logic.getCertificateDetails(certificationId);
        } catch (NumberFormatException | EmptyResultDataAccessException e) {
            model.addAttribute("certificateIsNull", "No such certificate! Try again!");
            return PAGE_JSP;
        }
        model.addAttribute("certificate", certificate);

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
