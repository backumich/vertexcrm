package ua.com.vertex.dao;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface CertificateDaoInf {

    @SuppressWarnings("unused")
    Certificate getCertificateById(int certificateId);

    @SuppressWarnings("unused")
    List<Certificate> getAllCertificateByUserId(int userId);

    int assignCertificateToUser(int userId, int certificateId);

}
