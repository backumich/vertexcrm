package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

@Service
public class CertDetailsPageLogicImpl implements CertDetailsPageLogic {
    private final UserDaoInf userDao;
    private final CertificateDaoInf certificateDao;
    private final ImageStorage storage;

    @Override
    public Certificate getCertificateDetails(int certificationId) {
        Certificate certificate = null;
        if (certificationId > 0) {
            certificate = certificateDao.getCertificateById(certificationId);
        }
        return certificate;
    }

    @Override
    //todo: take a look on this method again please
    public User getUserDetails(int userId) {
        User user;
        user = userDao.getUser(userId);

        if (user != null) {
            storage.setImageData(user.getPhoto());
            if (user.getPhoto() != null) {
                user.setPhoto(new byte[0]);
            }
            user.setPassportScan(null);
        }

        return user;
    }

    @Autowired
    public CertDetailsPageLogicImpl(UserDaoInf userDao, CertificateDaoInf certificateDao, ImageStorage storage) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.storage = storage;
    }
}
