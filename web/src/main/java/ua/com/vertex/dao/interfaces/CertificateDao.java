package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Certificate;

import java.util.List;

public interface CertificateDao {

    //todo: it is not unused any more, but suppress warning is here
    @SuppressWarnings("unused")
    Certificate getCertificateById(int certificateId);

    List<Certificate> getAllCertificatesByUserId(int userId);

}
