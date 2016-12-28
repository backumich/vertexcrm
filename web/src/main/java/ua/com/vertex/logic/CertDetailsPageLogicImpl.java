package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import java.util.Optional;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {
    private final UserDaoInf userDao;
    private final CertificateDaoInf certificateDao;

    @Override
    public Optional<Certificate> getCertificateDetails(int certificationId) {
        return certificateDao.getCertificateById(certificationId);
    }

    @Override
    public Optional<User> getUserDetails(int userId) {
        return userDao.getUser(userId);
    }

    @Autowired
    public CertDetailsPageLogicImpl(UserDaoInf userDao, CertificateDaoInf certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }
}
