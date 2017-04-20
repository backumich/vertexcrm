package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccountingDaoImpl implements AccountingDaoInf {

    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COURSE_ID = "courseId";
    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    public AccountingDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getCourseUsers(int courseId) {

        LOGGER.debug(String.format("Try select all users by course id = (%s), from db.Accounting", courseId));

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name FROM Users u" +
                "  INNER JOIN Accounting a ON u.user_id = a.user_id WHERE course_id = :courseId";
        return jdbcTemplate.query(query, new MapSqlParameterSource(COURSE_ID, courseId), (resultSet, i) -> new User.Builder().
                setUserId(resultSet.getInt(COLUMN_USER_ID)).
                setEmail(resultSet.getString(COLUMN_USER_EMAIL)).
                setFirstName(resultSet.getString(COLUMN_FIRST_NAME)).
                setLastName(resultSet.getString(COLUMN_LAST_NAME)).getInstance());
    }
}
