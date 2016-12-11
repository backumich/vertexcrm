package ua.com.vertex.dao.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.UserDaoInf;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class UserDaoRealizationTest {

    @Autowired
    UserDaoInf underTest;


    @Test
    @Ignore("Not implemented")
    public void isRegisteredEmailReturnNotNull() throws Exception {
        int result = underTest.isRegisteredEmail("");

        assertNotNull("Maybe method was changed", result);
    }

    @Test
    @Ignore("Not implemented")
    public void isRegisteredEmailReturnNotEmpty() throws Exception {
        int result = underTest.isRegisteredEmail("3");

        //assertFalse(result.a());
    }

    @Test
    @Ignore("Not implemented")
    public void getAllCertificateByUserIdReturnCorectData() throws Exception {
//        ArrayList<Certificate> certificates = new ArrayList<>();
//        certificates.add(new Certificate.Builder()
//                .setCertificationId(1)
//                .setUserId(1)
//                .setCertificationDate(LocalDate.parse("2016-12-01"))
//                .setCourseName("Java Professional")
//                .setLanguage("Java")
//                .getInstance());
//
//        assertEquals("Maybe mapping for this method was changed",
//                certificates, underTest.getAllCertificatesByUserId(1));
    }

}