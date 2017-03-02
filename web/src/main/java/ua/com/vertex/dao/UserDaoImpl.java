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
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.Storage;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository

public class UserDaoImpl implements UserDaoInf {
    private static final String USER_ID = "userId";

    private final String COLUMN_USER_ID = "user_id";
    private final String COLUMN_USER_EMAIL = "email";
    private final String COLUMN_FIRST_NAME = "first_name";
    private final String COLUMN_LARST_NAME = "last_name";

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
    @Transactional(propagation = Propagation.MANDATORY)
    public int addUserForCreateCertificate(User user) {
        String query = "INSERT INTO Users (email, first_name, last_name) " +
                "VALUES (:email, :first_name, :last_name)";

        LOGGER.debug(String.format("Try add user -(%s) ;", user));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, addParametrToMapSqlParameterSourceFromUser(user), keyHolder);

        LOGGER.debug(String.format("User added, user id -(%s) ;", keyHolder.getKey().toString()));
        return keyHolder.getKey().intValue();
    }

    private MapSqlParameterSource addParametrToMapSqlParameterSourceFromUser(User user) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_USER_EMAIL, user.getEmail());
        source.addValue(COLUMN_FIRST_NAME, user.getFirstName());
        source.addValue(COLUMN_LARST_NAME, user.getLastName());
        return source;
    }

    @Override
    public List<User> searchUser(String userData) throws Exception {

        String query = "SELECT user_id, email, first_name,last_name FROM Users WHERE email LIKE  '%" + userData +
                "%' OR  first_name LIKE '%" + userData + "%' OR  last_name LIKE '%" + userData + "%'";

        LOGGER.debug(String.format("Search users by -(%s) ;", userData));
        return jdbcTemplate.query(query, (rs, i) -> new User.Builder()
                .setUserId(rs.getInt(COLUMN_USER_ID))
                .setEmail(rs.getString(COLUMN_USER_EMAIL))
                .setFirstName(rs.getString(COLUMN_FIRST_NAME))
                .setLastName(rs.getString(COLUMN_LARST_NAME))
                .getInstance());
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

    @Autowired
    public UserDaoImpl(DataSource dataSource, Storage storage) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.storage = storage;
    }
}
