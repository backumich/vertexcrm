package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertificateDaoTest {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private CertificateDaoInf certificateDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Test
    public void jdbcTemplateShouldNotBeNull() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    public void daoShouldReturnCertificateOptionalForCertificateExistingInDatabase() {
        Optional<Certificate> optional = certificateDao.getCertificateById(222);
        assertNotNull(optional);
        optional.ifPresent(certificate -> assertEquals(222, certificate.getCertificationId()));
    }

    @Test
    public void daoShouldReturnCertificateOptionalForCertificateNotExistingInDatabase() {
        Optional<Certificate> optional = certificateDao.getCertificateById(55555);
        assertNotNull(optional);
        assertEquals(new Certificate(), optional.orElse(new Certificate()));
    }

    @Test
    public void getAllCertificateByUserIdReturnNotNull() throws Exception {
        List<Certificate> result = certificateDao.getAllCertificatesByUserId(-1);
        assertNotNull("Maybe method was changed", result);
    }

    @Test
    public void getAllCertificateByUserIdReturnNotEmpty() throws Exception {
        List<Certificate> result = certificateDao.getAllCertificatesByUserId(1);
        assertFalse(result.isEmpty());
    }

    @Test
    public void getAllCertificateByUserIdReturnCorectData() throws Exception {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder()
                .setCertificationId(1)
                .setUserId(0)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage(null)
                .getInstance());

        assertEquals("Maybe method was changed",
                certificates, certificateDao.getAllCertificatesByUserId(1));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test(expected = NoSuchElementException.class)
    public void getCertificateByIdReturnNull() throws Exception {
            certificateDao.getCertificateById(-1).get();
    }


    @Test
    public void getCertificateByIdReturnReturnCorectData() throws Exception {
        Certificate result = new Certificate.Builder()
                .setCertificationId(1)
                .setUserId(1)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();

        if (certificateDao.getCertificateById(1).isPresent()) {
            assertEquals("Maybe method was changed",
                    result, certificateDao.getCertificateById(1).get());
        }
    }

    @Test
    @Transactional
    public void addCertificateReturnCorectCertificationId() throws Exception {
        Certificate certificate = new Certificate.Builder()
                .setUserId(1)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance();
        int result = certificateDao.addCertificate(certificate);
        assertEquals("", result, 501);
    }
}
