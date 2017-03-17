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
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.LogInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static ua.com.vertex.beans.Role.ADMIN;
import static ua.com.vertex.beans.Role.USER;

@Repository
@SuppressWarnings("SqlDialectInspection")
public class UserDaoImpl implements UserDaoInf {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String PHOTO = "photo";
    private static final String PASSPORT_SCAN = "passportScan";

    @Override
    public Optional<User> getUser(int userId) {
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone, role_id FROM Users WHERE user_id=:userId";

        LOGGER.debug(logInfo.getId() + "Retrieving user, id=" + userId);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), new UserRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug(logInfo.getId() + "No user id=" + userId);
        }

        LOGGER.debug(logInfo.getId() + "Retrieved user, id=" + userId);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String query = "SELECT user_id, email, password, first_name, last_name, passport_scan, photo, discount, " +
                "phone, role_id FROM Users WHERE email=:email";

        LOGGER.debug(logInfo.getId() + "Retrieving user, email=" + email);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(EMAIL, email), new UserRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug(logInfo.getId() + "No user email=" + email);
        }

        LOGGER.debug(logInfo.getId() + "Retrieved user, email=" + email);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> logIn(String email) {
        String query = "SELECT email, password, role_id FROM Users WHERE email=:email";

        LOGGER.debug("Retrieving user password, role, email=" + email);

        MapSqlParameterSource parameters = new MapSqlParameterSource(EMAIL, email);

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query, parameters, new UserRowMapperLogIn());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("No email=" + email);
        }

        LOGGER.debug("Retrieved user password, role, email=" + email);

        return Optional.ofNullable(user);
    }

    @Override
    public void deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE user_id=:userId";
        jdbcTemplate.update(query, new MapSqlParameterSource(USER_ID, userId));
    }

    @Override
    public List<Integer> getAllUserIds() {
        String query = "SELECT user_id FROM Users ORDER BY user_id";
        return jdbcTemplate.query(query, new MapSqlParameterSource(), (resultSet, i) -> resultSet.getInt("user_id"));
    }

    @Override
    public void saveImage(int userId, byte[] image, String imageType) throws Exception {
        String query;
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(USER_ID, userId);

        LOGGER.debug(logInfo.getId() + "Saving image, user id=" + userId + ", " + imageType);

        if (PHOTO.equals(imageType)) {
            query = "UPDATE Users SET photo=:photo WHERE user_id=:userId";
            parameters.addValue(PHOTO, image);

        } else if (PASSPORT_SCAN.equals(imageType)) {
            query = "UPDATE Users SET passport_scan=:passportScan WHERE user_id=:userId";
            parameters.addValue(PASSPORT_SCAN, image);

        } else {
            throw new RuntimeException("Image not saved: wrong image type description");
        }

        LOGGER.debug(logInfo.getId() + "image saved");

        jdbcTemplate.update(query, parameters);
    }

    @Override
    public Optional<byte[]> getImage(int userId, String imageType) {
        byte[] image;
        String query;

        LOGGER.debug(logInfo.getId() + "Retrieving image, user id=" + userId + ", " + imageType);

        if (PHOTO.equals(imageType)) {
            query = "SELECT photo FROM Users WHERE user_id=:userId";

        } else if (PASSPORT_SCAN.equals(imageType)) {
            query = "SELECT passport_scan FROM Users WHERE user_id=:userId";

        } else {
            throw new RuntimeException("Wrong image type description");
        }

        image = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), byte[].class);

        LOGGER.debug(logInfo.getId() + "image retrieved");

        return Optional.ofNullable(image);
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

    @Override
    public Optional<User> getUserDetailsByID(int userID) throws SQLException {
        String query = "SELECT u.user_id, u.email, u.password, u.first_name, u.last_name, u.passport_scan, " +
                "u.photo, u.discount, u.phone, u.role_id FROM Users u WHERE u.user_id=:userId";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new MapSqlParameterSource("userId", userID), (rs, i) -> {
            User user = null;
            if (rs.getRow() == 1) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPassportScan(rs.getBytes("passport_scan"));
                user.setPhoto(rs.getBytes("photo"));
                user.setDiscount(rs.getInt("discount"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getInt("role_id") == 1 ? ADMIN : USER);
            }
            return user;
        }));
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        LOGGER.debug("Get a list of all users");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u";
        return jdbcTemplate.query(query, (resultSet, i) -> new User.Builder().
                setUserId(resultSet.getInt("user_id")).
                setEmail(resultSet.getString("email")).
                setFirstName(resultSet.getString("first_name")).
                setLastName(resultSet.getString("last_name")).
                setPhone(resultSet.getString("phone")).getInstance());
    }

    @Override
    public EnumMap<Role, Role> getAllRoles() {
        LOGGER.debug("Get a list of all users roles");

        String query = "SELECT r.role_id, r.name FROM Roles r";
        return jdbcTemplate.query(query, rs -> {
            EnumMap<Role, Role> allRoles = new EnumMap<>(Role.class);
            while (rs.next()) {
                allRoles.put(rs.getString("name").equals("ADMIN") ? Role.ADMIN : Role.USER,
                        rs.getString("name").equals("ADMIN") ? Role.ADMIN : Role.USER);
            }
            return allRoles;
        });
    }

    @Override
    public int saveUserData(User user) {
        String query = "UPDATE Users " +
                "SET email = :email , " +
                "first_name = :first_name, " +
                "last_name = :last_name, " +
                "passport_scan = :passport_scan, " +
                "photo = :photo, " +
                "discount = :discount, " +
                "phone = :phone, " +
                "role_id = :role_id" +
                " WHERE user_id = :user_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", user.getEmail());
        parameters.addValue("first_name", user.getFirstName());
        parameters.addValue("last_name", user.getLastName());
        parameters.addValue("passport_scan", user.getPassportScan());
        parameters.addValue("photo", user.getPhoto());
        parameters.addValue("discount", user.getDiscount());
        parameters.addValue("phone", user.getPhone());
        parameters.addValue("role_id", user.getRole().getId());
        parameters.addValue("user_id", user.getUserId());

        return jdbcTemplate.update(query, parameters);
    }

    public int activateUser(String email) {
        String query = "UPDATE Users " +
                "SET is_active = 1 " +
                "WHERE email = :email";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", email);
        return jdbcTemplate.update(query, parameters);
    }

    @Autowired
    public UserDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
