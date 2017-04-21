package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.LogInfo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CertificateLogicImplTest {

    private final String MSG = "Maybe method was changed";
    private CertificateLogicImpl certificateLogic;
    private Certificate certificate;
    private User user;
    @Mock
    private CertificateDaoInf certificateDao;

    @Mock
    private UserDaoInf userDao;

    @Mock
    private LogInfo logInfo;

    @Mock
    private Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        certificateLogic = new CertificateLogicImpl(userDao, certificateDao, logInfo);
        certificate = new Certificate.Builder().setUserId(1).setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional").setLanguage("Java").getInstance();
        user = new User.Builder().setUserId(1).setEmail("test@mail.ru").setFirstName("test").setLastName("test")
                .getInstance();
    }

    @Test
    public void daoNotBeNull() throws Exception {
        assertNotNull(userDao);
        assertNotNull(certificateDao);
    }

    @Test
    public void getCertificateByIdIsCalledOnCertificateDao() throws Exception {
        certificateLogic.getCertificateById(1);
        verify(certificateDao).getCertificateById(1);
    }

    @Test
    public void getCertificateByIdReturnNull() throws Exception {
        assertNull(MSG, certificateLogic.getCertificateById(1));
    }

    @Test
    public void getAllCertificateByUserEmailIsCalledOnCertificateDao() throws Exception {
        certificateLogic.getAllCertificatesByUserEmail("test");
        verify(certificateDao).getAllCertificatesByUserEmail("test");
    }

    @Test
    public void getAllCertificateByUserEmailNeverReturnNull() throws Exception {
        assertNotNull(MSG, certificateLogic.getAllCertificatesByUserEmail("test"));
    }

    @Test
    public void addCertificateIsCalledInCertificateDao() throws Exception {
        certificateLogic.addCertificate(certificate);
        verify(certificateDao).addCertificate(certificate);
    }

    @Test
    public void addCertificateAndCreateUser() throws Exception {
        certificateLogic.addCertificateAndCreateUser(certificate, user);
        verify(certificateDao).addCertificate(certificate);
        verify(userDao).addUserForCreateCertificate(user);
    }

    @Test
    public void setUserAndCertificateInvokesDao() throws SQLException {
        when(certificateDao.getCertificateByUid("1492779828793888"))
                .thenReturn(Optional.of(new Certificate.Builder().setUserId(22).getInstance()));
        when(userDao.getUser(22)).thenReturn(Optional.of(new User()));

        certificateLogic.getUserAndCertificate("1492779828793888", model);
        verify(certificateDao, times(1)).getCertificateByUid("1492779828793888");
        verify(userDao, times(1)).getUser(22);
    }

    @Test
    public void generateCertificateUid() {
        assertTrue(certificateLogic.generateCertificateUid().length() == 16);
    }
}
