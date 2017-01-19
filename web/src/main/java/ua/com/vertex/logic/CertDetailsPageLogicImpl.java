package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import java.util.Optional;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {
    private final CertificateDaoInf certificateDao;

    @Override
    public Optional<Certificate> getCertificateDetails(int certificationId) {
        return certificateDao.getCertificateById(certificationId);
    }

    @Autowired
    public CertDetailsPageLogicImpl(CertificateDaoInf certificateDao) {
        this.certificateDao = certificateDao;
    }
}
