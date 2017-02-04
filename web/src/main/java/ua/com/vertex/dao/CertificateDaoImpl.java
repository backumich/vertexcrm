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
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.utils.LogInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class CertificateDaoImpl implements CertificateDaoInf {

    private static final String USER_ID = "userId";
    private static final String CERTIFICATE_ID = "certificateId";

    private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class);
    private static final String LOG_CERT_IN = "Retrieving certificate id=";
    private static final String LOG_CERT_OUT = "Retrieved certificate id=";
    private static final String LOG_NO_CERT = "No certificate in DB, id=";
    private static final String LOG_ALLCERT_OUT = "Retrieved all certificates by id=";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;

    @Override
    public List<Certificate> getAllCertificatesByUserId(int userId) {

        String query = "SELECT certification_id, certification_date, course_name "
                + "FROM Certificate WHERE user_id =:userId";

        LOGGER.debug(LOG_ALLCERT_OUT + userId);

        return jdbcTemplate.query(query, new MapSqlParameterSource(USER_ID, userId), new ShortCertificateRowMapper());
    }

    @Override
    public Optional<Certificate> getCertificateById(int certificateId) {
        String query = "SELECT certification_id, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE certification_id =:certificateId";

        LOGGER.debug(logInfo.getId() + "Retrieving certificate id=" + certificateId);

        Certificate certificate = null;
        try {
            certificate = jdbcTemplate.queryForObject(query,
                    new MapSqlParameterSource(CERTIFICATE_ID, certificateId), new CertificateRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug(logInfo.getId() + "No certificate in DB, id=" + certificateId);
        }

        LOGGER.debug(logInfo.getId() + "Retrieved certificate id=" + certificateId);

        return Optional.ofNullable(certificate);
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

    @Autowired
    public CertificateDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
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
