package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.Storage;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class CertificateDaoImpl implements CertificateDaoInf {
    private static final String USER_ID = "userId";
    private static final String CERTIFICATE_ID = "certificateId";
    private static final String CERTIFICATION_DATE = "certificationDate";
    private static final String COURSE_NAME = "courseName";
    private static final String LANGUAGE = "language";
    private static final String COLUMN_CERTIFICATE_ID = "certification_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_CERTIFICATION_DATE = "certification_date";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_LANGUAGE = "language";
    private static final String LOG_CERT_IN = "Retrieving certificate id=";
    private static final String LOG_CERT_OUT = "Retrieved certificate id=";
    private static final String LOG_NO_CERT = "No certificate in DB, id=";
    private static final String LOG_ALL_CERTIFICATE_OUT = "Retrieved all certificates by id=";

    private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Storage storage;
    private final UserDaoInf userDaoInf;

    @Autowired
    public CertificateDaoImpl(DataSource dataSource, Storage storage, UserDaoInf userDaoInf) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.storage = storage;
        this.userDaoInf = userDaoInf;
    }

    @Override
    public List<Certificate> getAllCertificatesByUserId(int userId) {

        String query = "SELECT certification_id, certification_date, course_name "
                + "FROM Certificate WHERE user_id =:userId";

        LOGGER.debug(LOG_ALL_CERTIFICATE_OUT + userId);

        return jdbcTemplate.query(query, new MapSqlParameterSource(USER_ID, userId), new ShortCertificateRowMapper());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int addCertificate(Certificate certificate) {
        String query = "INSERT INTO Certificate (user_id,certification_date, course_name, language) " +
                "VALUES ( :userId, :certificationDate, :courseName, :language)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, addParametrToMapSqlParameterSourceFromCertificate(certificate), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int addCertificateAndCreateUser(Certificate certificate, User user) {

        int userID = userDaoInf.addUserForCreateCertificate(user);

        certificate.setUserId(userID);

        return addCertificate(certificate);

    }

    private MapSqlParameterSource addParametrToMapSqlParameterSourceFromCertificate(Certificate certificate) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(USER_ID, certificate.getUserId());
        source.addValue(CERTIFICATION_DATE, Date.valueOf(certificate.getCertificationDate()));
        source.addValue(COURSE_NAME, certificate.getCourseName());
        source.addValue(LANGUAGE, certificate.getLanguage());
        return source;
    }

    @Override
    public Optional<Certificate> getCertificateById(int certificateId) {
        String query = "SELECT certification_id, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE certification_id =:certificateId";

        LOGGER.debug(storage.getSessionId() + LOG_CERT_IN + certificateId);

        Certificate certificate;
        try {
            certificate = jdbcTemplate.queryForObject(query,
                    new MapSqlParameterSource(CERTIFICATE_ID, certificateId), new CertificateRowMapper());
        } catch (EmptyResultDataAccessException e) {
            certificate = null;
            LOGGER.error(storage.getSessionId() + LOG_NO_CERT + certificateId);
        }

        LOGGER.debug(storage.getSessionId() + LOG_CERT_OUT + certificateId);

        return Optional.ofNullable(certificate);
    }

    private static final class CertificateRowMapper implements RowMapper<Certificate> {
        public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Certificate.Builder()
                    .setCertificationId(resultSet.getInt(COLUMN_CERTIFICATE_ID))
                    .setUserId(resultSet.getInt(COLUMN_USER_ID))
                    .setCertificationDate(resultSet.getDate(COLUMN_CERTIFICATION_DATE).toLocalDate())
                    .setCourseName(resultSet.getString(COLUMN_COURSE_NAME))
                    .setLanguage(resultSet.getString(COLUMN_LANGUAGE))
                    .getInstance();
        }
    }

    private static final class ShortCertificateRowMapper implements RowMapper<Certificate> {

        public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Certificate.Builder()
                    .setCertificationId(resultSet.getInt(COLUMN_CERTIFICATE_ID))
                    .setUserId(0)
                    .setCertificationDate(resultSet.getDate(COLUMN_CERTIFICATION_DATE).toLocalDate())
                    .setCourseName(resultSet.getString(COLUMN_COURSE_NAME))
                    .setLanguage(null)
                    .getInstance();
        }

    }
}
