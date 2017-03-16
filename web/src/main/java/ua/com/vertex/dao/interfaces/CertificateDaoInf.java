package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDaoInf {

    Optional<Certificate> getCertificateById(int certificateId);

    List<Certificate> getAllCertificatesByUserId(int userId);

    int addCertificate(Certificate certificate) throws Exception;

    List<Certificate> getAllCertificatesByUserIdFullData(int userId);

}
