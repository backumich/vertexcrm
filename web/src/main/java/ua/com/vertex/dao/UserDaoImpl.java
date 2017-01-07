package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.beans.UserLogIn;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.Storage;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ua.com.vertex.utils.UserRole.ADMIN;
import static ua.com.vertex.utils.UserRole.USER;

@Repository
@SuppressWarnings("SqlDialectInspection")
public class UserDaoImpl implements UserDaoInf {
    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final String LOG_USER_IN = "Retrieving user id=";
    private static final String LOG_USER_OUT = "Retrieved user id=";
    private static final String LOG_NO_USER_ID = "No user id=";
    private static final String LOG_LOGIN_IN = "Retrieving user password and role by email=";
    private static final String LOG_LOGIN_OUT = "Retrieved user password and role by email=";
    private static final String LOG_NO_EMAIL = "No email=";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Storage storage;

    @Override
    public Optional<User> getUser(int userId) {
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone, role_id FROM Users WHERE user_id=:userId";

        LOGGER.info(storage.getId() + LOG_USER_IN + userId);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), new UserRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(storage.getId() + LOG_NO_USER_ID + userId);
        }

        LOGGER.info(storage.getId() + LOG_USER_OUT + userId);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserLogIn> logIn(String email) {
        String query = "SELECT password, role_id FROM Users WHERE email=:email";

        LOGGER.info(storage.getId() + LOG_LOGIN_IN + email);

        MapSqlParameterSource parameters = new MapSqlParameterSource(EMAIL, email);

        UserLogIn userLogIn = null;
        try {
            userLogIn = jdbcTemplate.queryForObject(query, parameters, new UserLogInRawMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(storage.getId() + LOG_NO_EMAIL + email);
        }

        LOGGER.info(storage.getId() + LOG_LOGIN_OUT + email);

        return Optional.ofNullable(userLogIn);
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
                    .setRole(resultSet.getInt("role_id"))
                    .getInstance();
        }
    }

    private static final class UserLogInRawMapping implements RowMapper<UserLogIn> {
        @Override
        public UserLogIn mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserLogIn.Builder()
                    .setPassword(resultSet.getString("password"))
                    .setUserRole(resultSet.getInt("role_id") == 1 ? ADMIN : USER)
                    .getInstance();
        }
    }

    @Autowired
    public UserDaoImpl(DataSource dataSource, Storage storage) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.storage = storage;
    }
}