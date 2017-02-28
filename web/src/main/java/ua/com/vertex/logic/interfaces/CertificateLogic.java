package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateLogic {

    List<Certificate> getAllCertificatesByUserId(int userId);

    List<Certificate> getAllCertificatesByUserIdFullData(int userId);

    Optional<Certificate> getCertificateById(int certificateId);
}
