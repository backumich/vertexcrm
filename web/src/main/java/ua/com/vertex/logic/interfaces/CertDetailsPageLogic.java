package ua.com.vertex.logic.interfaces;

import org.springframework.ui.Model;
import ua.com.vertex.beans.Certificate;

import java.util.Optional;

public interface CertDetailsPageLogic {

    Optional<Certificate> getCertificateDetails(int certificationId);

    int decodeId(String certificateIdEncoded, Model model);

    void setUserAndCertificate(int certificationId, Model model);
}
