package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

import java.util.List;
import java.util.Optional;

public interface CertificateDaoInf {

    Optional<Certificate> getCertificateById(int certificateId);

    List<Certificate> getAllCertificatesByUserId(int userId);

    int addCertificate(Certificate certificate);

    int addCertificateAndCreateUser(Certificate certificate, User user);

}
