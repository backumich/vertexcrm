package ua.com.vertex.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.CertificateDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class CertificateDaoImplTest {

    @Autowired
    CertificateDao underTest;


    @Test
    public void getAllCertificateByUserIdReturnNotNull() throws Exception {
        List<Certificate> result = underTest.getAllCertificatesByUserId(-1);

        assertNotNull("Maybe method was changed", result);
    }

    @Test
    public void getAllCertificateByUserIdReturnNotEmpty() throws Exception {
        List<Certificate> result = underTest.getAllCertificatesByUserId(1);

        assertFalse(result.isEmpty());

    }

    @Test
    public void getAllCertificateByUserIdReturnCorectData() throws Exception {
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(new Certificate.Builder()
                .setCertificationId(1)
                .setUserId(1)
                .setCertificationDate(LocalDate.parse("2016-12-01"))
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance());

        assertEquals("Maybe mapping for this method was changed",
                certificates, underTest.getAllCertificatesByUserId(1));
    }

}