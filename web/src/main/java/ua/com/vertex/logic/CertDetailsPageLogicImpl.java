package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.Aes;
import ua.com.vertex.utils.LogInfo;

import java.util.Optional;

import static ua.com.vertex.beans.Certificate.EMPTY_CERTIFICATE;
import static ua.com.vertex.beans.User.EMPTY_USER;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {

    private final CertificateDaoInf certificateDao;
    private final UserLogic userLogic;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(CertDetailsPageLogicImpl.class);

    private static final int WRONG_ID = -1;
    private static final String USER = "user";
    private static final String CERTIFICATE = "certificate";
    private static final String CERTIFICATE_LINK = "certificateLink";
    private static final String ERROR = "error";
    private static final String KEY = "ArgentinaJamaica";

    @Override
    public Optional<Certificate> getCertificateDetails(int certificationId) {
        return certificateDao.getCertificateById(certificationId);
    }

    @Override
    public int decodeId(String certificateIdEncoded, Model model) {
        int certificateId = WRONG_ID;
        try {
            certificateId = Integer.parseInt(Aes.decrypt(certificateIdEncoded, KEY));
            model.addAttribute(CERTIFICATE_LINK, certificateIdEncoded);
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e, e);
        }

        return certificateId;
    }

    @Override
    public void setUserAndCertificate(int certificationId, Model model) {
        LOGGER.debug(logInfo.getId() + "Processing request with certificateId=" + certificationId);

        Certificate certificate = getCertificateDetails(certificationId).orElse(EMPTY_CERTIFICATE);
        if (!EMPTY_CERTIFICATE.equals(certificate)) {
            model.addAttribute(CERTIFICATE, certificate);
            User user = userLogic.getUserById(certificate.getUserId()).orElse(EMPTY_USER);
            model.addAttribute(USER, user);
        } else {
            model.addAttribute(ERROR, "No certificate with this ID!");
        }
    }

    @Autowired
    public CertDetailsPageLogicImpl(CertificateDaoInf certificateDao, UserLogic userLogic, LogInfo logInfo) {
        this.certificateDao = certificateDao;
        this.userLogic = userLogic;
        this.logInfo = logInfo;
    }
}
