package ua.com.vertex.dao.interfaces;


import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Certificate;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

public interface CertificateDaoInf {

    Optional<Certificate> getCertificateById(int certificateId) throws DataAccessException;

    Optional<Certificate> getCertificateByUid(String certificateUid) throws DataAccessException;

    List<Certificate> getAllCertificatesByUserEmail(String eMail) throws DataAccessException;

    int addCertificate(Certificate certificate) throws DataAccessException;

    List<Certificate> getAllCertificatesByUserIdFullData(int userId) throws DataAccessException;

}
