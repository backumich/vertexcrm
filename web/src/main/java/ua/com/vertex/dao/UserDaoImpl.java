package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.LogInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ua.com.vertex.utils.Role.ADMIN;
import static ua.com.vertex.utils.Role.USER;

@Repository
@SuppressWarnings("SqlDialectInspection")
public class UserDaoImpl implements UserDaoInf {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private static final String LOG_USER_ID_IN = "Retrieving user, id=";
    private static final String LOG_USER_ID_OUT = "Retrieved user, id=";
    private static final String LOG_NO_USER_ID = "No user id=";
    private static final String LOG_USER_EMAIL_IN = "Retrieving user, email=";
    private static final String LOG_USER_EMAIL_OUT = "Retrieved user, email=";
    private static final String LOG_NO_USER_EMAIL = "No user email=";
    private static final String LOG_LOGIN_IN = "Retrieving user password and role, email=";
    private static final String LOG_LOGIN_OUT = "Retrieved user password and role, email=";
    private static final String LOG_NO_EMAIL = "No email=";
    private static final String LOG_SAVE_IMAGE_IN = "Saving image, user id=";
    private static final String LOG_SAVE_IMAGE_OUT = "Saved image";
    private static final String LOG_GET_IMAGE_IN = "Retrieving image, user id=";
    private static final String LOG_GET_IMAGE_OUT = "Retrieved image";

    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";

    @Override
    public Optional<User> getUser(int userId) {
        String query = "SELECT user_id, email, password, first_name, last_name, discount, " +
                "phone, role_id FROM Users WHERE user_id=:userId";

        LOGGER.debug(logInfo.getId() + LOG_USER_ID_IN + userId);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), new UserRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(logInfo.getId() + LOG_NO_USER_ID + userId);
        }

        LOGGER.debug(logInfo.getId() + LOG_USER_ID_OUT + userId);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String query = "SELECT user_id, email, password, first_name, last_name, discount, " +
                "phone, role_id FROM Users WHERE email=:email";

        LOGGER.debug(logInfo.getId() + LOG_USER_EMAIL_IN + email);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(EMAIL, email), new UserRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(logInfo.getId() + LOG_NO_USER_EMAIL + email);
        }

        LOGGER.debug(logInfo.getId() + LOG_USER_EMAIL_OUT + email);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> logIn(String email) {
        String query = "SELECT email, password, role_id FROM Users WHERE email=:email";

        LOGGER.debug(LOG_LOGIN_IN + email);

        MapSqlParameterSource parameters = new MapSqlParameterSource(EMAIL, email);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, parameters, new UserRowMapperLogIn());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug(LOG_NO_EMAIL + email);
        }

        LOGGER.debug(LOG_LOGIN_OUT + email);

        return Optional.ofNullable(user);
    }

    @Override
    public void deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE user_id=:userId";
        jdbcTemplate.update(query, new MapSqlParameterSource(USER_ID, userId));
    }

    @Override
    public List<Integer> getAllUserIds() {
        String query = "SELECT user_id FROM Users order by user_id";
        return jdbcTemplate.query(query, (resultSet, i) -> resultSet.getInt("user_id"));
    }

    @Override
    public void saveImage(int userId, byte[] image, String imageType) throws Exception {
        String query;
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(USER_ID, userId);

        LOGGER.debug(logInfo.getId() + LOG_SAVE_IMAGE_IN + userId + ", " + imageType);

        if (PHOTO.equals(imageType)) {
            query = "UPDATE Users SET photo=:photo WHERE user_id=:userId";
            parameters.addValue(PHOTO, image);

        } else if (PASSPORT_SCAN.equals(imageType)) {
            query = "UPDATE Users SET passport_scan=:passportScan WHERE user_id=:userId";
            parameters.addValue(PASSPORT_SCAN, image);

        } else {
            throw new RuntimeException("Image not saved: wrong image type description");
        }

        LOGGER.debug(logInfo.getId() + LOG_SAVE_IMAGE_OUT);

        jdbcTemplate.update(query, parameters);
    }

    @Override
    public Optional<byte[]> getImage(int userId, String imageType) {
        byte[] image;
        String query;

        LOGGER.debug(logInfo.getId() + LOG_GET_IMAGE_IN + userId + ", " + imageType);

        if (PHOTO.equals(imageType)) {
            query = "SELECT photo FROM Users WHERE user_id=:userId";

        } else if (PASSPORT_SCAN.equals(imageType)) {
            query = "SELECT passport_scan FROM Users WHERE user_id=:userId";

        } else {
            throw new RuntimeException("Wrong image type description");
        }

        image = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId),
                byte[].class);

        LOGGER.debug(logInfo.getId() + LOG_GET_IMAGE_OUT);

        return Optional.ofNullable(image);
    }

    private static final class UserRowMapping implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User.Builder()
                    .setUserId(resultSet.getInt("user_id"))
                    .setEmail(resultSet.getString("email"))
                    .setPassword(resultSet.getString("password"))
                    .setFirstName(resultSet.getString("first_name"))
                    .setLastName(resultSet.getString("last_name"))
                    .setDiscount(resultSet.getInt("discount"))
                    .setPhone(resultSet.getString("phone"))
                    .setRole(resultSet.getInt("role_id") == 1 ? ADMIN : USER)
                    .getInstance();
        }
    }

    private static final class UserRowMapperLogIn implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User.Builder()
                    .setEmail(resultSet.getString("email"))
                    .setPassword(resultSet.getString("password"))
                    .setRole(resultSet.getInt("role_id") == 1 ? ADMIN : USER)
                    .getInstance();
        }
    }

    @Autowired
    public UserDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
