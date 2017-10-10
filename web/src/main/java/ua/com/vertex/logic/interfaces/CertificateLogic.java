package ua.com.vertex.logic.interfaces;

import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Map;

public interface CertificateLogic {

    List<Certificate> getAllCertificatesByUserEmail(String eMail);

    List<Certificate> getAllCertificatesByUserIdFullData(int userId);

    //Optional<Certificate> getCertificateById(int certificateId);

    int addCertificate(Certificate certificate);

    int addCertificateAndCreateUser(Certificate certificate, User user);

    String generateCertificateUid();

    Map<String, Object> getUserAndCertificate(String certificateUid);
}
