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
    @WithMockUser
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
    public void getAllCertificateByUserEmailReturnCorrectData() throws Exception {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder().setCertificationId(1).setUserId(0)
                .setCertificationDate(LocalDate.parse("2016-12-01")).setCourseName("Java Professional")
                .setLanguage(null).getInstance());
        assertEquals(MSG, certificates, certificateDao.getAllCertificatesByUserEmail("email1"));
    }

    @Test(expected = NoSuchElementException.class)
    @WithMockUser
    public void getCertificateByIdReturnNull() throws Exception {
        //noinspection ConstantConditions,ResultOfMethodCallIgnored
        certificateDao.getCertificateById(-1).get();
    }

    @Test
    @WithMockUser
    public void getCertificateByIdReturnReturnCorrectData() throws Exception {
        if (certificateDao.getCertificateById(1).isPresent()) {
            assertEquals(MSG, new Certificate.Builder().setCertificationId(1).setUserId(1)
                    .setCertificationDate(LocalDate.parse("2016-12-01")).setCourseName("Java Professional")
                    .setLanguage("Java").getInstance(), certificateDao.getCertificateById(1).get());
        }
    }

    @Test
    public void addCertificateReturnCorrectCertificationId() throws Exception {
        assertEquals("", certificateDao.addCertificate(new Certificate.Builder().setUserId(1)
                .setCertificationDate(LocalDate.parse("2016-12-01")).setCourseName("Java Professional")
                .setLanguage("Java").getInstance()), 501);
    }
}
