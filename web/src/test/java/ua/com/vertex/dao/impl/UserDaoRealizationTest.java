package ua.com.vertex.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.UserDaoRealizationInf;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class UserDaoRealizationTest {

    @Autowired
    private UserDaoRealizationInf underTest;

    @Test
    public void isRegisteredEmailReturnNotZero() throws Exception {
        int result = underTest.isRegisteredEmail("email1");
        assertEquals(1, result);
    }

    @Test
    public void isRegisteredEmailReturnZero() throws Exception {
        int result = underTest.isRegisteredEmail("chewed.mole2@gmail.com");
        assertEquals(0, result);
    }

    @Test
    public void getCertificateByIdReturnEmptyResultDataAccessException() throws Exception {
        int cnt = underTest.isRegisteredEmail(null);
        Assert.assertEquals(0, cnt);
    }

    @Test
    public void registrationUser_UserNotNull() throws Exception {
        User user = new User();
        user.setEmail("chewed.mole@gmail.com");
        user.setPassword("SuperMegaStrongPassword");
        user.setFirstName("James");
        user.setLastName("Bond");
        user.setPhone("007");

        int id = underTest.registrationUser(user);
        Assert.assertNotNull(id);

        User testUser = underTest.getUser(id);

        Assert.assertEquals("chewed.mole@gmail.com", testUser.getEmail());
        Assert.assertEquals("SuperMegaStrongPassword", testUser.getPassword());
        Assert.assertEquals("James", testUser.getFirstName());
        Assert.assertEquals("Bond", testUser.getLastName());
        Assert.assertEquals("007", testUser.getPhone());
    }

    @Test(expected = DataAccessException.class)
    public void registrationUser_UserNull() throws Exception {
        User user = new User();
        underTest.registrationUser(user);
    }
}