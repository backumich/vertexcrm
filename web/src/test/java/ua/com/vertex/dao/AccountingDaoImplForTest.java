package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Accounting;

import javax.sql.DataSource;
import java.util.Optional;

import static ua.com.vertex.dao.AccountingDaoImpl.COURSE_ID;
import static ua.com.vertex.dao.AccountingDaoImpl.USER_ID;

@Repository
class AccountingDaoImplForTest {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(AccountingDaoImplForTest.class);

    int createAccounting(Accounting accounting) throws DataAccessException {
        LOGGER.debug(String.format("Try insert accounting  = (%s)", accounting));
        String query = "INSERT INTO  Accounting (user_id, course_id, course_coast, debt) " +
                "VALUES (:user_id, :course_id, :course_coast, :debt) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource("user_id", accounting.getUserId());
        source.addValue("course_id", accounting.getCourseId());
        source.addValue("course_coast", accounting.getCourseCoast());
        source.addValue("debt", accounting.getDebt());


        jdbcTemplate.update(query, source, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @SuppressWarnings("SameParameterValue")
    Optional<Accounting> getAccountingByCourseIdAndUserId( int courseId, int userId) throws DataAccessException {
        LOGGER.debug(String.format("Try select accounting by course id = (%s) and user id = (%s)", courseId, userId));
        String query = "SELECT deal_id, user_id, course_id, course_coast,debt FROM Accounting " +
                "WHERE course_id = :courseId AND user_id = :userId";

        MapSqlParameterSource source = new MapSqlParameterSource(COURSE_ID, courseId);
        source.addValue(USER_ID, userId);

        Accounting accounting = null;
        try {
            accounting = jdbcTemplate.queryForObject(query, source,
                    (resultSet, i) -> new Accounting.Builder().
                            setDealId(resultSet.getInt("deal_id")).
                            setUserId(resultSet.getInt("user_id")).
                            setCourseId(resultSet.getInt("course_id")).
                            setCourseCoast(resultSet.getDouble("course_coast")).
                            setDept(resultSet.getDouble("debt")).getInstance());
        } catch (DataAccessException e) {
            LOGGER.warn(e);
        }
        return Optional.ofNullable(accounting);
    }

    @Autowired
    AccountingDaoImplForTest(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
