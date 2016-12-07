package ua.com.vertex.logic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.logic.interfaces.CertificateAssignLogic;

@Service
public class CertificateAssignImpl implements CertificateAssignLogic {

    private static final Logger log = Logger.getLogger(CertificateAssignLogic.class);

    @Autowired
    private CertificateDaoInf certificateDao;

    @Override
    public void assignCertificateToUser(int userId, int certificateId) {
        certificateDao.assignCertificateToUser(userId, certificateId);

    }
}
