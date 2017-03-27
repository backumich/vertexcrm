//package ua.com.vertex.dao;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import ua.com.vertex.beans.User;
//import ua.com.vertex.context.TestConfig;
//
//import static org.junit.Assert.assertEquals;
//
//@SuppressWarnings("Duplicates")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@WebAppConfiguration
//@ActiveProfiles("test")
//public class UserDaoRealizationTest {
//
//    @Autowired
//    private UserDaoRealizationInf underTest;
//
//    @Test
//    public void isRegisteredEmailReturnNotZero() throws Exception {
//        Boolean result = underTest.isRegisteredEmail("email1");
//        assertEquals(true, result);
//    }
//
//    @Test
//    public void isRegisteredEmailReturnZero() throws Exception {
//        Boolean result = underTest.isRegisteredEmail("chewed.mole2@gmail.com");
//        assertEquals(false, result);
//    }
//
//    @Test
//    public void isRegisteredEmailByNull() throws Exception {
//        Boolean cnt = underTest.isRegisteredEmail(null);
//        Assert.assertEquals(false, cnt);
//    }
//
//    @Test
//    public void registrationUser_UserNotNull() throws Exception {
//        User user = new User();
//        user.setEmail("chewed.mole@gmail.com");
//        user.setPassword("SuperMegaStrongPassword");
//        user.setFirstName("James");
//        user.setLastName("Bond");
//        user.setPhone("007");
//
//        int id = underTest.registrationUser(user);
//        Assert.assertNotNull(id);
//
//        User testUser = underTest.getUser(id);
//
//        Assert.assertEquals("chewed.mole@gmail.com", testUser.getEmail());
//        Assert.assertEquals("SuperMegaStrongPassword", testUser.getPassword());
//        Assert.assertEquals("James", testUser.getFirstName());
//        Assert.assertEquals("Bond", testUser.getLastName());
//        Assert.assertEquals("007", testUser.getPhone());
//    }
//
//    @Test(expected = DataAccessException.class)
//    public void registrationUser_UserNull() throws Exception {
//        User user = new User();
//        underTest.registrationUser(user);
//    }
//}