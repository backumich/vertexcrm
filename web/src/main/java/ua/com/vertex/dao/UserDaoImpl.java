package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.*;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.Storage;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("SqlDialectInspection")
public class UserDaoImpl implements UserDaoInf {
    private static final String USER_ID = "userId";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final String LOG_USER_IN = "Retrieving user id=";
    private static final String LOG_USER_OUT = "Retrieved user id=";
    private static final String LOG_NO_USER = "No user id=";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Storage storage;

    @Override
    public Optional<User> getUser(int userId) {
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone FROM Users WHERE user_id=:userId";

        LOGGER.info(storage.getSessionId() + LOG_USER_IN + userId);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), new UserRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(storage.getSessionId() + LOG_NO_USER + userId);
        }

        LOGGER.info(storage.getSessionId() + LOG_USER_OUT + userId);

        return Optional.ofNullable(user);
    }

    private static final class UserRowMapping implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            LobHandler handler = new DefaultLobHandler();
            return new User.Builder()
                    .setUserId(resultSet.getInt("user_id"))
                    .setEmail(resultSet.getString("email"))
                    .setPassword(resultSet.getString("password"))
                    .setFirstName(resultSet.getString("first_name"))
                    .setLastName(resultSet.getString("last_name"))
                    .setPassportScan(handler.getBlobAsBytes(resultSet, "passport_scan"))
                    .setPhoto(handler.getBlobAsBytes(resultSet, "photo"))
                    .setDiscount(resultSet.getInt("discount"))
                    .setPhone(resultSet.getString("phone"))
                    .getInstance();
        }
    }

    @Override
    public void deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE user_id=:id";
        jdbcTemplate.update(query, new MapSqlParameterSource(USER_ID, userId));
    }

    @Override
    public List<Integer> getAllUserIds() {
        String query = "SELECT user_id FROM Users order by user_id";
        return jdbcTemplate.query(query, (resultSet, i) -> resultSet.getInt("user_id"));
    }

    @Override
    public User getUserDetails(int userID) {
        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.passport_scan, u.photo, u.discount, u.phone," +
                "   c.certification_id, c.certification_date, c.course_name, c.language," +
                "   a.deal_id, a.course_coast, a.debt, " +
                "   p.payment_id, p.amount" +
                "       FROM Users u" +
                "        LEFT JOIN Certificate c ON u.user_id = c.user_id" +
                "        LEFT JOIN Accounting a ON u.user_id = a.user_id" +
                "        LEFT JOIN Payments p ON a.deal_id = p.deal_id" +
                "        WHERE u.user_id = :userID";

        return jdbcTemplate.query(query, new MapSqlParameterSource("userID", userID), new UserDetailsRowMapping());
    }

    private static final class UserDetailsRowMapping implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            User user = null;
//            User user = new User.Builder().
//                    setUserId(rs.getInt("user_id")).
//                    setEmail(rs.getString("email")).
//                    setFirstName(rs.getString("first_name")).
//                    setLastName(rs.getString("last_name")).
//                    setPassportScan(rs.getBytes("passport_scan")).
//                    setPhoto(rs.getBytes("photo")).
//                    setDiscount(rs.getInt("discount")).
//                    setPhone(rs.getString("phone")).
//                    getInstance();

            Certificate certificate = new Certificate();
            Accounting accounting = new Accounting();
            Payments payments = new Payments();

            ArrayList<Certificate> listCertificate = new ArrayList<>();
            ArrayList<Accounting> listAccounting = new ArrayList<>();
            ArrayList<Payments> listPayments = new ArrayList<>();

            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPassportScan(rs.getBytes("passport_scan"));
                    user.setPhoto(rs.getBytes("photo"));
                    user.setDiscount(rs.getInt("discount"));
                    user.setPhone(rs.getString("phone"));
                }

                int certification_id = rs.getInt("certification_id");
                if (!rs.wasNull() && rs.getInt("certification_id") > 0) {
                    certificate.setCertificationId(certification_id);
                    certificate.setCertificationDate(rs.getDate("certification_date").toLocalDate());
                    certificate.setCourseName(rs.getString("course_name"));
                    certificate.setLanguage(rs.getString("language"));
                    listCertificate.add(certificate);
                }
            }

            user.setCertificate(listCertificate);

            return user;

        }
    }

    @Override
    public List<UserMainData> getListUsers() throws DataAccessException {
        LOGGER.info("Adding a new user into database");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u";
        return jdbcTemplate.query(query, new UserDaoImpl.ViewAllUserRowMapping());
    }

    private static final class ViewAllUserRowMapping implements RowMapper<UserMainData> {
        public UserMainData mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserMainData.Builder().
                    setUserId(resultSet.getInt("user_id")).
                    setEmail(resultSet.getString("email")).
                    setFirstName(resultSet.getString("first_name")).
                    setLastName(resultSet.getString("last_name")).
                    setPhone(resultSet.getString("phone")).getInstance();
        }
    }

    @Autowired
    public UserDaoImpl(DataSource dataSource, Storage storage) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.storage = storage;
    }
}
