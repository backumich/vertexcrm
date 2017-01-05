package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDao;
import ua.com.vertex.logic.interfaces.CertificateLogic;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private static final Logger LOGGER = LogManager.getLogger(CertificateLogicImpl.class);

    private CertificateDao certificateDao;

    @Autowired
    public CertificateLogicImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    public List<Certificate> getAllCertificatesByUserId(int userId) {
        LOGGER.debug(String.format("Call - certificateDao.getAllCertificateByUserId(%s);", Integer.toString(userId)));
        return certificateDao.getAllCertificatesByUserId(userId);
    }

    public Optional<Certificate> getCertificateById(int certificateId) {
        LOGGER.debug(String.format("Call - certificateDao.getCertificateById(%s);", Integer.toString(certificateId)));
        return certificateDao.getCertificateById(certificateId);
    }
}
