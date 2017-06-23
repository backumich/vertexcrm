package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import javax.sql.DataSource;

@Repository
public class AccountingDaoImpl implements AccountingDaoInf {

    static final String COURSE_ID = "courseId";
    static final String USER_ID = "userId";
    private static final String DEBT = "debt";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(AccountingDaoImpl.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateUserDept(int courseId, int userId, double amount) {

        LOGGER.debug(String.format("Try update user dept by course id = (%s) and user id = (%s), from db.Accounting",
                courseId, userId));

        String query = "UPDATE Accounting SET debt = debt - :debt WHERE course_id = :courseId AND user_id = :userId";

        MapSqlParameterSource source = new MapSqlParameterSource(DEBT, amount);
        source.addValue(COURSE_ID, courseId);
        source.addValue(USER_ID, userId);

        jdbcTemplate.update(query, source);
    }

    @Autowired
    public AccountingDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}


