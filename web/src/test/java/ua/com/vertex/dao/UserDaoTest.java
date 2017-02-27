package ua.com.vertex.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserDaoTest {

    private final String MSG = "Maybe method was changed";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private User user;

    @Autowired
    private UserDaoInf userDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Before
    public void setUp() throws Exception {
        user = new User.Builder().setUserId(22).setEmail("email").setFirstName("FirstName")
                .setLastName("LastName").setDiscount(0).getInstance();
    }

    @Test
    public void jdbcTemplateShouldNotBeNull() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    public void daoShouldReturnUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUser(22);
        assertNotNull(optional);
        //noinspection OptionalGetWithoutIsPresent
        assertEquals(22, optional.get().getUserId());
    }

    @Test
    public void daoShouldReturnUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUser(55555);
        assertNotNull(optional);
        assertEquals(new User(), optional.orElse(new User()));
    }

    @Test
    public void searchUserReturnEmtyResult() throws Exception {
        List<User> users = userDao.searchUser("TTTTTTTTT");
        assertNotNull(MSG, users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void searchUserReturnCorrectData() throws Exception {
        List<User> users = userDao.searchUser("Name");
        assertFalse(MSG, users.isEmpty());
        assertEquals(MSG, users.size(), 3);
        assertEquals(MSG, users.get(1), user);

    }

    @Test
    @Transactional
    public void addUserForCreateCertificateReturnCorrectData() throws Exception {
        assertEquals(MSG, userDao.addUserForCreateCertificate(user), 36);
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void addUserForCreateCertificateReturnExc() throws Exception {
        userDao.addUserForCreateCertificate(user);
    }
}
