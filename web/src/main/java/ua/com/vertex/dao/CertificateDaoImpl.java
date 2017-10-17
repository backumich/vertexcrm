package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ua.com.vertex.dao.UserDaoImpl.EMAIL;

@Repository
public class CertificateDaoImpl implements CertificateDaoInf {

    private static final String CERTIFICATION_ID = "certification_id";
    private static final String USER_ID = "user_id";
    private static final String CERTIFICATION_DATE = "certification_date";
    private static final String COURSE_NAME = "course_name";
    private static final String LANGUAGE = "language";
    private static final String CERTIFICATE_UID = "certificate_uid";

    private static final Logger logger = LogManager.getLogger(CertificateDaoImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Certificate> getAllCertificatesByUserEmail(String eMail) {

        String query = "SELECT c.certification_id, c.certificate_uid, c.user_id, c.certification_date, c.course_name " +
                "FROM Certificate c INNER JOIN  Users u ON c.user_id = u.user_id WHERE email = :email";

        logger.debug("Retrieved all certificates by eMail=" + eMail);

        return jdbcTemplate.query(query, new MapSqlParameterSource(EMAIL, eMail),
                (resultSet, i) -> new Certificate.Builder()
                        .setCertificationId(resultSet.getInt(CERTIFICATION_ID))
                        .setCertificateUid(String.valueOf(resultSet.getLong(CERTIFICATE_UID)))
                        .setUserId(0)
                        .setCertificationDate(resultSet.getDate(CERTIFICATION_DATE).toLocalDate())
                        .setCourseName(resultSet.getString(COURSE_NAME))
                        .getInstance());
    }

    private MapSqlParameterSource addParameterToMapSqlParameterSourceFromCertificate(Certificate certificate) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(USER_ID, certificate.getUserId());
        source.addValue(CERTIFICATION_DATE, Date.valueOf(certificate.getCertificationDate()));
        source.addValue(COURSE_NAME, certificate.getCourseName());
        source.addValue(LANGUAGE, certificate.getLanguage());
        source.addValue(CERTIFICATE_UID, Long.parseLong(certificate.getCertificateUidWithoutDashes()));
        return source;
    }

    @Override
    public int addCertificate(Certificate certificate) {
        String query = "INSERT INTO Certificate (user_id, certification_date, course_name, language, certificate_uid)" +
                "VALUES ( :user_id, :certification_date, :course_name, :language, :certificate_uid)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, addParameterToMapSqlParameterSourceFromCertificate(certificate), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Certificate> getAllCertificatesByUserIdFullData(int userId) {

        String query = "SELECT certification_id, certificate_uid, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE user_id =:user_id";

        logger.debug("Retrieved all certificates by id=" + userId);

        return jdbcTemplate.query(query, new MapSqlParameterSource(USER_ID, userId), new CertificateRowMapper());
    }

    @Override
    public Optional<Certificate> getCertificateById(int certificateId) {

        String query = "SELECT certification_id, certificate_uid, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE certification_id =:certification_id";

        Certificate certificate;
        try {
            certificate = jdbcTemplate.queryForObject(query,
                    new MapSqlParameterSource(CERTIFICATION_ID, certificateId), new CertificateRowMapper());
        } catch (EmptyResultDataAccessException e) {
            certificate = null;
        }

        logger.debug((certificate == null ? "No certificate in DB, ID=" : "Retrieved certificate ID=") + certificateId);

        return Optional.ofNullable(certificate);
    }

    @Override
    public Optional<Certificate> getCertificateByUid(String certificateUid) {

        String query = "SELECT certification_id, certificate_uid, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE certificate_uid =:certificate_uid";

        Certificate certificate;
        try {
            certificate = jdbcTemplate.queryForObject(query,
                    new MapSqlParameterSource(CERTIFICATE_UID, certificateUid), new CertificateRowMapper());
        } catch (IncorrectResultSizeDataAccessException | DataIntegrityViolationException e) {
            certificate = null;
        }

        logger.debug((certificate == null ? "No certificate in DB, UID=" : "Retrieved certificate UID=")
                + certificateUid);

        return Optional.ofNullable(certificate);
    }

    private static final class CertificateRowMapper implements RowMapper<Certificate> {
        public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Certificate.Builder()
                    .setCertificationId(resultSet.getInt(CERTIFICATION_ID))
                    .setCertificateUid(resultSet.getString(CERTIFICATE_UID))
                    .setUserId(resultSet.getInt(USER_ID))
                    .setCertificationDate(resultSet.getDate(CERTIFICATION_DATE).toLocalDate())
                    .setCourseName(resultSet.getString(COURSE_NAME))
                    .setLanguage(resultSet.getString(LANGUAGE))
                    .getInstance();
        }
    }

    @Autowired
    public CertificateDaoImpl(@Qualifier(value = "DS") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
