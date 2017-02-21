package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private static final Logger LOGGER = LogManager.getLogger(CertificateLogicImpl.class);
    private static final String LOG_ALLCERT = "Call - certificateDao.getAllCertificateByUserId(%s);";
    private static final String LOG_CERT = "Call - certificateDao.getCertificateById(%s);";
    private static final String LOG_ADD_CERT = "Call - certificateDao.addCertificate ;";
    private static final String LOG_ADD_CERT_AND_NEW_USER = "Call - certificateDao.addCertificateAndCreateUser ;";



    private CertificateDaoInf certificateDaoInf;

    @Autowired
    public CertificateLogicImpl(CertificateDaoInf certificateDaoInf) {
        this.certificateDaoInf = certificateDaoInf;
    }

    public List<Certificate> getAllCertificatesByUserId(int userId) {
        LOGGER.debug(String.format(LOG_ALLCERT, Integer.toString(userId)));
        return certificateDaoInf.getAllCertificatesByUserId(userId);
    }

    public Optional<Certificate> getCertificateById(int certificateId) {
        LOGGER.debug(String.format(LOG_CERT, Integer.toString(certificateId)));
        return certificateDaoInf.getCertificateById(certificateId);
    }

    public int addCertificate(Certificate certificate) {
        LOGGER.debug(LOG_ADD_CERT);
        return certificateDaoInf.addCertificate(certificate);
    }

    public int addCertificateAndCreateUser(Certificate certificate, User user) {
        LOGGER.debug(LOG_ADD_CERT_AND_NEW_USER);
        return certificateDaoInf.addCertificateAndCreateUser(certificate, user);
    }
}
