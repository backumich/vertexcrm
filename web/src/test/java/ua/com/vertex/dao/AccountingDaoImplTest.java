package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AccountingDaoImplTest {

    @Autowired
    private AccountingDaoInf accountingDaoInf;

    @Test
    public void getCourseUsersReturnCorrectData() throws Exception {
        LobHandler handler = new DefaultLobHandler();
        assertEquals("Maybe method was changed", accountingDaoInf.getCourseUsers(1).get(0),
                new User.Builder().setUserId(1).setEmail("email1").setFirstName("FirstName")
                        .setLastName("LastName").setDiscount(0).getInstance());

    }
}
