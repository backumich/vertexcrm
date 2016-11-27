package ua.com.vertex.dao;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface CertificateDao {

    @SuppressWarnings("unused")
    Certificate getCertificateById(int certificateId);

    List<Certificate> getAllCertificateByUserId(int userId);

}
