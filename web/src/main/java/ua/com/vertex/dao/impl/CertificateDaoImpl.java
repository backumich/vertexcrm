package ua.com.vertex.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.CertificateDao;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CertificateDaoImpl implements CertificateDao {

    private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @SuppressWarnings("SqlDialectInspection")
    public List<Certificate> getAllCertificateByUserId(int userId) {
        String query = "SELECT certification_id, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE user_id =:userId";

        LOGGER.info("Call - jdbcTemplate.query ");
        return jdbcTemplate.query(query, new MapSqlParameterSource("userId", userId), new CertificateRowMapper());
    }

    @SuppressWarnings("SqlDialectInspection")
    public Certificate getCertificateById(int certificateId) {
        String query = "SELECT certification_id, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE certification_id =:certificateId";
        return jdbcTemplate.queryForObject(query,
                new MapSqlParameterSource("certificateId", certificateId), new CertificateRowMapper());
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public int assignCertificateToUser(int userId, int certificateId) {
        String query = "UPDATE Certificate SET user_id=:userId WHERE  certification_id=:certificateId";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId);
        parameterSource.addValue("certificateId", certificateId);
        int toReturn = jdbcTemplate.update(query, parameterSource);
        return toReturn;
    }



    private static final class CertificateRowMapper implements RowMapper<Certificate> {
        public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Certificate.Builder()
                    .setCertificationId(resultSet.getInt("certification_id"))
                    .setUserId(resultSet.getInt("user_id"))
                    .setCertificationDate(resultSet.getDate("certification_date").toLocalDate())
                    .setCourseName(resultSet.getString("course_name"))
                    .setLanguage(resultSet.getString("language"))
                    .getInstance();
        }
    }
}
