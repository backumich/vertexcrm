package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(CertDetailsPageLogicImpl.class);
    private final UserDaoInf userDao;
    private final CertificateDaoInf certificateDao;
    private ImageStorage storage;

    @Override
    public Certificate getCertificateDetails(int certificationId) {
        Certificate certificate;

        LOGGER.debug("Accessing certificateDao, certificateID=" + certificationId);
        certificate = certificateDao.getCertificateById(certificationId);

        return certificate;
    }

    @Override
    public User getUserDetails(int userId) {
        User user;

        LOGGER.debug("Accessing userDao, userID=" + userId);
        user = userDao.getUser(userId);

        if (user != null) {
            storage.setImageData(user.getPhoto());
            if (user.getPhoto() != null)
                user.setPhoto(new byte[0]);
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
