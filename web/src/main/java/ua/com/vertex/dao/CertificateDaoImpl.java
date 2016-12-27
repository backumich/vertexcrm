package ua.com.vertex.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CertificateDaoImpl implements CertificateDao {

    //todo: same with SuppressWarnings
    @SuppressWarnings("WeakerAccess")
    public static final String USER_ID = "userId";
    @SuppressWarnings("WeakerAccess")
    public static final String CERTIFICATE_ID = "certificateId";

    private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Certificate> getAllCertificatesByUserId(int userId) {

        String query = "SELECT certification_id, certification_date, course_name "
                + "FROM Certificate WHERE user_id =:userId";

        LOGGER.debug("Getting all certificates for user with id: " + userId);

        return jdbcTemplate.query(query, new MapSqlParameterSource(USER_ID, userId), new ShortCertificateRowMapper());
    }

    public Certificate getCertificateById(int certificateId) throws EmptyResultDataAccessException {
        String query = "SELECT certification_id,user_id, certification_date, course_name, language " +
                "FROM Certificate WHERE certification_id =:certificateId";
        return jdbcTemplate.queryForObject(query,
                new MapSqlParameterSource(CERTIFICATE_ID, certificateId), new CertificateRowMapper());
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

    private static final class ShortCertificateRowMapper implements RowMapper<Certificate> {

        public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Certificate.Builder()
                    .setCertificationId(resultSet.getInt("certification_id"))
                    .setUserId(0)
                    .setCertificationDate(resultSet.getDate("certification_date").toLocalDate())
                    .setCourseName(resultSet.getString("course_name"))
                    .setLanguage(null)
                    .getInstance();
        }

    }
}
