package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.controllers.exceptionHandling.NoCertificateException;
import ua.com.vertex.controllers.exceptionHandling.ServiceException;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static ua.com.vertex.beans.Certificate.Builder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertificateDaoTest {

    private final String MSG = "Maybe method was changed";

    @Autowired
    private CertificateDaoInf certificateDao;

    private static final int EXISTING_ID = 222;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;

    @Test
    @WithAnonymousUser
    public void getCertificateByIdReturnsCertificateOptionalForCertificateExistingInDatabase() {
        Certificate certificate = certificateDao.getCertificateById(EXISTING_ID);
        assertNotNull(certificate);
        assertEquals(EXISTING_ID, certificate.getCertificationId());
    }

    @Test(expected = ServiceException.class)
    @WithMockUser
    public void getCertificateByIdReturnsNullOptionalForCertificateNotExistingInDatabase() {
        /*Certificate certificate =*/
        certificateDao.getCertificateById(NOT_EXISTING_ID);
//        assertNotNull(certificate);
//        assertEquals(null, certificate.orElse(null));
    }

    @Test
    public void getAllCertificateByUserEmailReturnNotNull() throws Exception {
        List<Certificate> result = certificateDao.getAllCertificatesByUserEmail("test");
        assertNotNull(MSG, result);
    }

    @Test
    public void getAllCertificateByUserEmailReturnNotEmpty() throws Exception {
        assertFalse(certificateDao.getAllCertificatesByUserEmail("22@test.com").isEmpty());
    }

    @Test
    public void getAllCertificateByUserEmailReturnsCorrectData() throws Exception {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Builder()
                .setCertificationId(1)
                .setCertificateUid("1492779828793891")
                .setUserId(0)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage(null)
                .getInstance());
        assertEquals(MSG, certificates, certificateDao.getAllCertificatesByUserEmail("email1"));
    }
//todo: повтор
//    @Test(expected = NoSuchElementException.class)
//    @WithAnonymousUser
//    public void getCertificateByIdReturnNull() throws Exception {
//
//        certificateDao.getCertificateById(-1).get();
//    }

    @Test
    @WithAnonymousUser
    public void getCertificateByIdReturnReturnsCorrectData() throws Exception {
//        if (certificateDao.getCertificateById(1).isPresent()) {
        assertEquals(MSG, new Builder()
                .setCertificationId(1)
                .setCertificateUid("1492779828793891")
                .setUserId(1)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance(), certificateDao.getCertificateById(1)/*.get()*/);
//        }
    }

    @Test
    @WithAnonymousUser
    public void addCertificateReturnCorrectCertificationId() throws Exception {

        Certificate certificate = new Certificate.Builder()
                .setUserId(44)
                .setCertificateUid("1492779828793891")
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
        int result = certificateDao.addCertificate(certificate);
        certificate.setCertificationId(result);

        assertEquals(MSG, certificate, certificateDao.getCertificateById(result));
    }

    @Test
    @WithAnonymousUser
    public void getCertificateByUidReturnsCertificate() {
        assertEquals(new Builder()
                .setCertificationId(222)
                .setCertificateUid("1492779828793888")
                .setUserId(22)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance(), certificateDao.getCertificateByUid("1492779828793888"));
    }

    @Test(expected = NoCertificateException.class)
    @WithAnonymousUser
    public void getCertificateByNonExistingUidReturnsEmptyOptional() {
        certificateDao.getCertificateByUid("0");
    }
}
