package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface CertificateLogic {

    List<Certificate> getAllCertificatesByUserId(int userId);

    Optional<Certificate> getCertificateById(int certificateId);

    int addCertificate(Certificate certificate) throws Exception;

    int addCertificateAndCreateUser(Certificate certificate, User user) throws Exception;
}
