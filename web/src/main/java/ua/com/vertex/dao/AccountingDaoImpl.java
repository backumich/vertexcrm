package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;

import javax.sql.DataSource;
import java.util.List;

import static ua.com.vertex.dao.UserDaoImpl.*;

@Repository
public class AccountingDaoImpl implements AccountingDaoInf {

    static final String COURSE_ID = "course_id";
    static final String USER_ID = "user_id";
    private static final String DEBT = "debt";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger Logger = LogManager.getLogger(AccountingDaoImpl.class);


    @Override
    public List<User> getCourseUsers(int courseId) {

        Logger.debug(String.format("Try select all users by course id = (%s), from db.Accounting", courseId));

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name FROM Users u" +
                "  INNER JOIN Accounting a ON u.user_id = a.user_id WHERE course_id = :course_id";
        return jdbcTemplate.query(query, new MapSqlParameterSource(COURSE_ID, courseId),
                (resultSet, i) -> new User.Builder().
                        setUserId(resultSet.getInt(USER_ID)).
                        setEmail(resultSet.getString(EMAIL)).
                        setFirstName(resultSet.getString(FIRST_NAME)).
                        setLastName(resultSet.getString(LAST_NAME)).getInstance());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateUserDept(int courseId, int userId, double amount) {
        Logger.debug(String.format("Call - accountingDaoInf.updateUserDept((%s), (%s) , (%f)) ;",
                courseId, userId, amount));

        String query = "UPDATE Accounting SET debt = debt - :debt WHERE course_id = :course_id AND user_id = :user_id";

        MapSqlParameterSource source = new MapSqlParameterSource(DEBT, amount);
        source.addValue(COURSE_ID, courseId);
        source.addValue(USER_ID, userId);

        Logger.debug(String.format("Try update user dept by course id = (%s) and user id = (%s), from db.Accounting",
                courseId, userId));
        jdbcTemplate.update(query, source);
    }

    @Autowired
    public AccountingDaoImpl(@Qualifier(value = "DS") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}


