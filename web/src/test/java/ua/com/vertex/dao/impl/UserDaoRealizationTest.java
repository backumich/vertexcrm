package ua.com.vertex.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.UserDaoRealizationInf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class UserDaoRealizationTest {

    @Autowired
    UserDaoRealizationInf underTest;


    @Test
    public void isRegisteredEmailReturnNotNull() throws Exception {
//        int result = underTest.isRegisteredEmail("");
//
//        assertNotNull("Maybe method was changed", result);
    }

    @Test
    public void isRegisteredEmailReturnNotEmpty() throws Exception {

    }


}