package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.util.Map;

@Controller
public class CertificateDetailsPageController {
    private static final String CERTIFICATE_DETAILS = "certificateDetails";

    private final CertificateLogic certLogic;

    @GetMapping(value = "/certificateDetails")
    public String showCertificateDetailsPage() {
        return CERTIFICATE_DETAILS;
    }

    @GetMapping(value = "/getCertificate")
    public String getCertificate(@RequestParam String certificateUid, Model model) {
        return process(certificateUid, model);
    }

    @GetMapping(value = "/getCertificate/{certificateUid}")
    public String getCertificateByCertificateUid(@PathVariable String certificateUid, Model model) {
        return process(certificateUid, model);
    }

    private String process(String certificateUid, Model model) {
        certificateUid = certificateUid.replaceAll("-", "");
        Map<String, Object> attributes = certLogic.getUserAndCertificate(certificateUid);
        model.addAllAttributes(attributes);

        return CERTIFICATE_DETAILS;
    }

    @Autowired
    public CertificateDetailsPageController(CertificateLogic certLogic) {
        this.certLogic = certLogic;
    }
}
