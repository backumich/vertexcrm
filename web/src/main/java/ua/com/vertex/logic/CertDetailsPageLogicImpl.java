package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;
import ua.com.vertex.utils.Storage;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {
    private final UserDaoInf userDao;
    private final CertificateDaoInf certificateDao;
    private final Storage storage;

    @Override
    public Certificate getCertificateDetails(int certificationId) {
        return certificateDao.getCertificateById(certificationId);
    }

    @Override
    public User getUserDetails(int userId) {
        User user = userDao.getUser(userId);
        storage.setImageData(user.getPhoto());

        return user;
    }

    @Autowired
    public CertDetailsPageLogicImpl(UserDaoInf userDao, CertificateDaoInf certificateDao, Storage storage) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.storage = storage;
    }
}
