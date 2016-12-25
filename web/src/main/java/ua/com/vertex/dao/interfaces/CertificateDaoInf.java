package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface CertificateDaoInf {

    //todo delete if doesn't used
    @SuppressWarnings("unused")
    Certificate getCertificateById(int certificateId);

    @SuppressWarnings("unused")
    List<Certificate> getAllCertificateByUserId(int userId);

}
