package ua.com.vertex.dao;

import org.junit.Assert;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserDaoTest {

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
    public void getListUsersNotEmpty() throws Exception {
        List<User> users = userDao.getListUsers();
        //assertEquals(4, users.size());
        assertEquals(false, users.isEmpty());
    }

    @Test
    public void getUserDetailsByIDForUserExistingInDatabase() throws Exception {
        User testUser = new User();
        testUser.setUserId(10);
        testUser.setEmail("emailTest");
        testUser.setFirstName("first_name");
        testUser.setLastName("last_name");
        testUser.setDiscount(0);
        testUser.setPhone("666666666");

        User user = userDao.getUserDetailsByID(10);
        Assert.assertNotNull(user);

        Assert.assertEquals(testUser.getUserId(), user.getUserId());
        Assert.assertEquals(testUser.getEmail(), user.getEmail());
        Assert.assertEquals(testUser.getFirstName(), user.getFirstName());
        Assert.assertEquals(testUser.getLastName(), user.getLastName());
        Assert.assertEquals(testUser.getDiscount(), user.getDiscount());
        Assert.assertEquals(testUser.getPhone(), user.getPhone());
    }

    @Test
    public void getUserDetailsByIDForUserNotExistingInDatabase() throws Exception {
        User user = userDao.getUserDetailsByID(1111);
        Assert.assertNull(user);
    }
}
