package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDaoInf {

    Optional<Certificate> getCertificateById(int certificateId);

    Optional<Certificate> getCertificateByUid(String certificateUid);

    List<Certificate> getAllCertificatesByUserEmail(String eMail);

    int addCertificate(Certificate certificate) throws Exception;

    List<Certificate> getAllCertificatesByUserIdFullData(int userId);

}
