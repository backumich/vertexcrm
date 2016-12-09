package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface CertificateDao {

    @SuppressWarnings("unused")
    Certificate getCertificateById(int certificateId);

    List<Certificate> getAllCertificatesByUserId(int userId);

}
