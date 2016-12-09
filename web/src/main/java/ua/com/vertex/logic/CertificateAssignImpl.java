package ua.com.vertex.logic;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertificateAssignLogic;

@Service
public class CertificateAssignImpl implements CertificateAssignLogic {

    private static final Logger log = Logger.getLogger(CertificateAssignLogic.class);

    private static CertificateDaoInf certificateDao;

    @Override
    public int assignCertificateToUser(int userId, int certificateId) {
        return certificateDao.assignCertificateToUser(userId, certificateId);

    }
}
