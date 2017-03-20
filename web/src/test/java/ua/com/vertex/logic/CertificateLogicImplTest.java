//package ua.com.vertex.logic;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ua.com.vertex.dao.interfaces.CertificateDaoInf;
//import ua.com.vertex.beans.Certificate;
//import ua.com.vertex.beans.User;
//import ua.com.vertex.dao.interfaces.UserDaoInf;
//
//import java.time.LocalDate;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.mockito.Mockito.verify;
//
//public class CertificateLogicImplTest {
//
//    private final String MSG = "Maybe method was changed";
//    private CertificateLogicImpl certificateLogic;
//    private Certificate certificate;
//    private User user;
//    @Mock
//    private CertificateDaoInf certificateDao;
//
//    @Mock
//    private UserDaoInf userDao;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        certificateLogic = new CertificateLogicImpl(certificateDao, userDao);
//        certificate = new Certificate.Builder().setUserId(1).setCertificationDate(LocalDate.parse("2016-12-01"))
//                .setCourseName("Java Professional").setLanguage("Java").getInstance();
//        user = new User.Builder().setUserId(1).setEmail("test@mail.ru").setFirstName("test").setLastName("test")
//                .getInstance();
//    }
//
//    @Test
//    public void daoNotBeNull() throws Exception {
//        assertNotNull(userDao);
//        assertNotNull(certificateDao);
//    }
//
//    @Test
//    public void getCertificateByIdIsCalledOnCertificateDao() throws Exception {
//        certificateLogic.getCertificateById(1);
//        verify(certificateDao).getCertificateById(1);
//    }
//
//    @Test
//    public void getCertificateByIdReturnNull() throws Exception {
//        assertNull(MSG, certificateLogic.getCertificateById(1));
//    }
//
//    @Test
//    public void getAllCertificateByUserIdIsCalledOnCertificateDao() throws Exception {
//        certificateLogic.getAllCertificatesByUserId(1);
//        verify(certificateDao).getAllCertificatesByUserId(1);
//    }
//
//    @Test
//    public void getAllCertificateByUserIdNeverReturnNull() throws Exception {
//        assertNotNull(MSG, certificateLogic.getAllCertificatesByUserId(-1));
//    }
//
//    @Test
//    public void addCertificateIsCalledInCertificateDao() throws Exception {
//        certificateLogic.addCertificate(certificate);
//        verify(certificateDao).addCertificate(certificate);
//    }
//
//    @Test
//    public void addCertificateAndCreateUser() throws Exception {
//        certificateLogic.addCertificateAndCreateUser(certificate, user);
//        verify(certificateDao).addCertificate(certificate);
//        verify(userDao).addUserForCreateCertificate(user);
//    }
//
//}