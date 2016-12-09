package ua.com.vertex.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDao;
import ua.com.vertex.dao.interfaces.UserDao;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    private static final Logger LOGGER = LogManager.getLogger(UserLogicImpl.class);

    private final CertificateDao certificateDao;
    private final UserDao userDao;

    @Autowired
    public UserLogicImpl(UserDao userDao, CertificateDao certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    public List<Certificate> getAllCertificatesByUserId(int userId) {
        LOGGER.debug(String.format("Call - certificateDao.getAllCertificateByUserId(%s);", Integer.toString(userId)));
        return certificateDao.getAllCertificatesByUserId(userId);
    }

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }


}
