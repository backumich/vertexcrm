package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {

    Optional<Certificate> getCertificateById(int certificateId);

    List<Certificate> getAllCertificatesByUserId(int userId);

}
