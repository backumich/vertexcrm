package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private static final Logger LOGGER = LogManager.getLogger(CertificateLogicImpl.class);
    private final UserDaoInf userDaoInf;
    private CertificateDaoInf certificateDaoInf;

    @Autowired
    public CertificateLogicImpl(CertificateDaoInf certificateDaoInf, UserDaoInf userDaoInf) {
        this.certificateDaoInf = certificateDaoInf;
        this.userDaoInf = userDaoInf;
    }

    public List<Certificate> getAllCertificatesByUserId(int userId) {
        LOGGER.debug(String.format("Call - certificateDao.getAllCertificateByUserId(%s);", Integer.toString(userId)));
        return certificateDaoInf.getAllCertificatesByUserId(userId);
    }

    public Optional<Certificate> getCertificateById(int certificateId) {
        LOGGER.debug(String.format("Call - certificateDao.getCertificateById(%s);", Integer.toString(certificateId)));
        return certificateDaoInf.getCertificateById(certificateId);
    }

    public int addCertificate(Certificate certificate) throws Exception {
        LOGGER.debug(String.format("Call - certificateDaoInf.addCertificate(%s) ;", certificate));
        return certificateDaoInf.addCertificate(certificate);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addCertificateAndCreateUser(Certificate certificate, User user) throws Exception {
        LOGGER.debug(String.format("Call - userDaoInf.addUserForCreateCertificate(%s) ;", user));
        int userID = userDaoInf.addUserForCreateCertificate(user);
        certificate.setUserId(userID);

        LOGGER.debug(String.format("Call - certificateDaoInf.addCertificate(%s) ;", certificate));
        return certificateDaoInf.addCertificate(certificate);
    }
}
