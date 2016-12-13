package ua.com.vertex.logic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.dao.CertificateDao;
import ua.com.vertex.logic.interfaces.CertificateAssignLogic;

@Service
public class CertificateAssignLogicImpl implements CertificateAssignLogic {

    private static final Logger log = Logger.getLogger(CertificateAssignLogic.class);

    @Autowired
    private CertificateDao certificateDao;

    @Override
    public int assignCertificateToUser(int userId, int certificateId) {
        return certificateDao.assignCertificateToUser(userId, certificateId);

    }
}
