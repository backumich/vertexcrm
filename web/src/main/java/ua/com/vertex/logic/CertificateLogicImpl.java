package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.controllers.exceptionHandling.NoCertificateException;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.*;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private static final Logger LOGGER = LogManager.getLogger(CertificateLogicImpl.class);

    private final UserDaoInf userDaoInf;
    private final CertificateDaoInf certificateDaoInf;
    private final LogInfo logInfo;

    private static final String USER = "user";
    private static final String CERTIFICATE = "certificate";

    public List<Certificate> getAllCertificatesByUserEmail(String eMail) {
        LOGGER.debug(String.format("Call - certificateDao.getAllCertificateByUserId(%s);", eMail));
        return certificateDaoInf.getAllCertificatesByUserEmail(eMail);
    }

    public List<Certificate> getAllCertificatesByUserIdFullData(int userId) {
        LOGGER.debug(String.format("Call - certificateDao.getAllCertificatesByUserIdFullData(%s);"
                , Integer.toString(userId)));
        return certificateDaoInf.getAllCertificatesByUserIdFullData(userId);
    }

    public Optional<Certificate> getCertificateById(int certificateId) {
        LOGGER.debug(String.format("Call - certificateDao.getCertificateById(%s);", Integer.toString(certificateId)));
        return certificateDaoInf.getCertificateById(certificateId);
    }

    public int addCertificate(Certificate certificate) throws Exception {
        LOGGER.debug(String.format("Call - certificateDaoInf.addCertificate(%s) ;", certificate));
        certificate.setCertificateUid(generateCertificateUid());
        return certificateDaoInf.addCertificate(certificate);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addCertificateAndCreateUser(Certificate certificate, User user) throws Exception {
        LOGGER.debug(String.format("Call - userDaoInf.addUserForCreateCertificate(%s) ;", user));
        int userID = userDaoInf.addUserForCreateCertificate(user);
        certificate.setUserId(userID);
        certificate.setCertificateUid(generateCertificateUid());

        LOGGER.debug(String.format("Call - certificateDaoInf.addCertificate(%s) ;", certificate));
        return certificateDaoInf.addCertificate(certificate);
    }

    @Override
    public String generateCertificateUid() {
        String part1 = String.valueOf(System.currentTimeMillis());
        String part2 = String.valueOf(100 + new Random().nextInt(900));

        return (part1 + part2).substring(0, 16);
    }

    @Override
    public Map<String, Object> getUserAndCertificate(String certificateUid) {
        LOGGER.debug(logInfo.getId() + "Processing request with certificateId=" + certificateUid);

        Map<String, Object> attributes = new HashMap<>();
        Certificate certificate = certificateDaoInf.getCertificateByUid(certificateUid)
                .orElseThrow(NoCertificateException::new);
        User user = userDaoInf.getUser(certificate.getUserId()).orElse(new User());
        attributes.put(CERTIFICATE, certificate);
        attributes.put(USER, user);

        return attributes;
    }

    @Autowired
    public CertificateLogicImpl(UserDaoInf userDaoInf, CertificateDaoInf certificateDaoInf, LogInfo logInfo) {
        this.userDaoInf = userDaoInf;
        this.certificateDaoInf = certificateDaoInf;
        this.logInfo = logInfo;
    }
}
