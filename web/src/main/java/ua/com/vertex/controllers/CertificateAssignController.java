package ua.com.vertex.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.vertex.logic.interfaces.CertificateAssignLogic;

@Controller
@SessionAttributes("userId, certificateId")
public class CertificateAssignController {
    private static final Logger LOGGER = LogManager.getLogger(CertificateAssignController.class);
    private final CertificateAssignLogic certificateAssignLogic;

    @Autowired
    public CertificateAssignController(CertificateAssignLogic certificateAssignLogic) {
        this.certificateAssignLogic = certificateAssignLogic;
    }

    @RequestMapping(value = "/assigncertificate", method = RequestMethod.GET)
    public String assigncertificate(@RequestParam("userId") int userId, @RequestParam("certificateId") int certificateId, Model model) {
        Integer updatedColumCount = 0;
        updatedColumCount = certificateAssignLogic.assignCertificateToUser(userId, certificateId);

        model.addAttribute("updatedColumCount", updatedColumCount);
        LOGGER.info("render jsp");
        return "assigncertificate";

    }
}

