package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import javax.sql.DataSource;

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

//    @Test
//    public void daoShouldReturnUserExistingInDatabase() {
//        User user = userDao.getUser(22);
//        assertEquals(22, user.getUserId());
//    }
//
//    @Test
//    public void daoShouldReturnUserForUserNotExistingInDatabase() {
//        User user = userDao.getUser(55555);
//        assertEquals(0, user.getUserId());
//    }
}
