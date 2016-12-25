package ua.com.vertex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@SuppressWarnings("SqlDialectInspection")
public class CertificateDaoImpl implements CertificateDaoInf {
    private static final String USER_ID = "userId";
    private static final String CERTIFICATE_ID = "certificateId";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Certificate> getAllCertificateByUserId(int userId) {
        String query = "SELECT certification_id, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE user_id =:userId";

        return jdbcTemplate.query(query, new MapSqlParameterSource(USER_ID, userId), new CertificateRowMapper());
    }

    @Override
    public Certificate getCertificateById(int certificateId) {
        String query = "SELECT certification_id, user_id, certification_date, course_name, language "
                + "FROM Certificate WHERE certification_id =:certificateId";

        //todo wrap with try-catch
        return jdbcTemplate.queryForObject(query,
                new MapSqlParameterSource(CERTIFICATE_ID, certificateId), new CertificateRowMapper());
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
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
