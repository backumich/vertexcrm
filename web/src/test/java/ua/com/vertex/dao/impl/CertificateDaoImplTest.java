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

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class CertificateDaoImplTest {

    @Autowired
    CertificateDao underTest;

    @Test
    public void getAllCertificateByUserId() throws Exception {
        List<Certificate> result = underTest.getAllCertificateByUserId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());

    }

}