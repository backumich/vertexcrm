package ua.com.vertex.dao;


import ua.com.vertex.dao.impl.Certificate;

import java.util.List;

public interface CertificateDaoInf {

    Certificate getCertificateById(int certificateId);
    List<Certificate> getAllCertificateByUserId(int userId);


}
