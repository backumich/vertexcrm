package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
    @Autowired
    private UserDaoInf userDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Test
    public void jdbcTemplateShouldNotBeNull() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    public void daoShouldReturnUserOptionalForUserExistingInDatabase() {
        Optional<User> optional = userDao.getUser(22);
        assertNotNull(optional);
        assertEquals(22, optional.get().getUserId());
    }

    @Test
    public void daoShouldReturnUserOptionalForUserNotExistingInDatabase() {
        Optional<User> optional = userDao.getUser(55555);
        assertNotNull(optional);
        assertEquals(new User(), optional.orElse(new User()));
    }

    @Test
    public void searchUser() throws Exception {
        List<User> users = userDao.searchUser("TTTTTTTTT");
        assertTrue(users.isEmpty());

        users = userDao.searchUser("Name");
        assertFalse(MSG, users.isEmpty());
        assertEquals(MSG, users.size(), 3);
        assertEquals(MSG, users.get(1), new User.Builder().setUserId(22).setEmail("email").setPassword("password")
                .setFirstName("FirstName").setLastName("LastName"));
    }
}
