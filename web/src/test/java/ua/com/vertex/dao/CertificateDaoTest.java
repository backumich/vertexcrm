package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static ua.com.vertex.beans.Certificate.Builder;
import static ua.com.vertex.beans.Certificate.EMPTY_CERTIFICATE;

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
    public void getCertificateByIdReturnsCertificateOptionalForCertificateExistingInDatabase() {
        Optional<Certificate> optional = certificateDao.getCertificateById(EXISTING_ID);
        assertNotNull(optional);

        //noinspection ConstantConditions
        assertEquals(EXISTING_ID, optional.get().getCertificationId());
    }

    @Test
    @WithMockUser
    public void getCertificateByIdReturnsNullOptionalForCertificateNotExistingInDatabase() {
        Optional<Certificate> optional = certificateDao.getCertificateById(NOT_EXISTING_ID);
        assertNotNull(optional);
        assertEquals(null, optional.orElse(null));
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

    @Test(expected = NoSuchElementException.class)
    public void getCertificateByIdReturnNull() throws Exception {

        certificateDao.getCertificateById(-1).get();
    }

    @Test
    public void getCertificateByIdReturnReturnsCorrectData() throws Exception {
        if (certificateDao.getCertificateById(1).isPresent()) {
            assertEquals(MSG, new Builder()
                    .setCertificationId(1)
                    .setCertificateUid("1492779828793891")
                    .setUserId(1)
                    .setCertificationDate(LocalDate.parse("2016-12-01"))
                    .setCourseName("Java Professional")
                    .setLanguage("Java")
                    .getInstance(), certificateDao.getCertificateById(1).get());
        }
    }

    @Test
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

        assertEquals(MSG, certificate, certificateDao.getCertificateById(result).orElse(EMPTY_CERTIFICATE));
    }

    @Test
    public void getCertificateByUidReturnsCertificate() {
        assertEquals(new Builder()
                .setCertificationId(222)
                .setCertificateUid("1492779828793888")
                .setUserId(22)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance(), certificateDao.getCertificateByUid("1492779828793888").orElse(EMPTY_CERTIFICATE));
    }

    @Test
    public void getCertificateByNonExistingUidReturnsEmptyOptional() {
        assertEquals(EMPTY_CERTIFICATE, certificateDao.getCertificateByUid("0").orElse(EMPTY_CERTIFICATE));
    }
}
