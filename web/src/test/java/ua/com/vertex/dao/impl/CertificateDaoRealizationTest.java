package ua.com.vertex.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainContext;
import ua.com.vertex.dao.CertificateDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainContext.class)

public class CertificateDaoRealizationTest {

    @Autowired
    public CertificateDao certificateDao;


    @Test
    public void testGetAllCertificateByUserId() {

    }

    @Test
    public void testGetCertificateById() {

    }

    @Test
    public void testAssignCertificateToUser() {

        // certificateDao.assignCertificateToUser(1, 2);

    }


}