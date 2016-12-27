package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDao;
import ua.com.vertex.logic.interfaces.CertificateLogic;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private static final Logger LOGGER = LogManager.getLogger(CertificateLogicImpl.class);

    private CertificateDao certificateDao;

    @Autowired
    public CertificateLogicImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    public Certificate getCertificateById(int certificateId) throws EmptyResultDataAccessException {
        LOGGER.debug(String.format("Call - certificateDao.getCertificateById(%s);", Integer.toString(certificateId)));
        return certificateDao.getCertificateById(certificateId);
    }
}
