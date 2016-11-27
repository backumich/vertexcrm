package ua.com.vertex.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.CertificateDao;
import ua.com.vertex.dao.UserDao;
import ua.com.vertex.logic.UserLogic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogicImpl implements UserLogic {

    private final CertificateDao certificateDao;

    private final UserDao userDao;

    @Autowired
    public UserLogicImpl(UserDao userDao, CertificateDao certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    public List<Certificate> getAllCertificateByUserId(int userId) {
        return certificateDao.getAllCertificateByUserId(userId);
    }

    @Override
    public List<String> getAllUserIds() {
        return userDao.getAllUserIds().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
    }


}
