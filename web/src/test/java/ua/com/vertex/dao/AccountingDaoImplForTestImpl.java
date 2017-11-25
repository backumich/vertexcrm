package ua.com.vertex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Accounting;
import ua.com.vertex.dao.interfaces.AccountingDaoImplForTest;

import javax.sql.DataSource;
import java.util.Optional;

import static ua.com.vertex.dao.AccountingDaoImpl.COURSE_ID;
import static ua.com.vertex.dao.AccountingDaoImpl.USER_ID;

@Repository
@Profile("test")
class AccountingDaoImplForTestImpl implements AccountingDaoImplForTest {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    AccountingDaoImplForTestImpl(@Qualifier(value = "DS") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @SuppressWarnings("SameParameterValue")
    public Optional<Accounting> getAccountingByCourseIdAndUserId(int courseId, int userId) {
        String query = "SELECT deal_id, user_id, course_id, course_coast,debt FROM Accounting " +
                "WHERE course_id = :course_id AND user_id = :user_id";

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
        } catch (DataAccessException ignored) {
        }
        return Optional.ofNullable(accounting);
    }
}
