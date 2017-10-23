package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ua.com.vertex.beans.PasswordResetDto;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.controllers.exceptionHandling.UpdatedPasswordNotSaved;
import ua.com.vertex.dao.interfaces.DaoUtilInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.DataNavigator;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.com.vertex.dao.AccountingDaoImpl.COURSE_ID;

@Repository
public class UserDaoImpl implements UserDaoInf {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    static final String EMAIL = "email";
    static final String FIRST_NAME = "first_name";
    static final String LAST_NAME = "last_name";
    private static final String IMAGE_PASSPORT_SCAN = "passportScan";
    private static final String USER_ID = "user_id";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    private static final String PASSPORT_SCAN = "passport_scan";
    private static final String PHOTO = "photo";
    private static final String DISCOUNT = "discount";
    private static final String ROLE_NAME = "name";
    private static final String IS_ACTIVE = "is_active";

    private DaoUtilInf daoUtil;

    @Override
    public Optional<User> getUser(int userId) {
        LOGGER.debug(String.format("Call -  getUser(%s) ;", userId));

        String query = "SELECT u.user_id, u.email, u.password, u.first_name, u.last_name, u.passport_scan, u.photo, " +
                "u.discount, u.phone, r.name FROM Users u INNER JOIN Roles r  ON u.role_id = r.role_id" +
                " WHERE user_id=:user_id";
        User user = null;

        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), new UserRowMapping());
            LOGGER.debug("Retrieved user, id=" + userId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("No user id=" + userId);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.debug(String.format("Call -  getUserByEmail(%s) ;", email));

        String query = "SELECT u.user_id, u.email, u.password, u.first_name, u.last_name, u.passport_scan, u.photo, " +
                "u.discount, u.phone, r.name FROM Users u INNER JOIN Roles r  ON u.role_id = r.role_id" +
                " WHERE email=:email";
        User user = null;

        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(EMAIL, email), new UserRowMapping());
            LOGGER.debug("Retrieved user, email=" + email);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("No user email=" + email);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> logIn(String email) {
        LOGGER.debug(String.format("Call -  logIn(%s) ;", email));

        String query = "SELECT u.email, u.password, r.name FROM Users u INNER JOIN Roles r ON u.role_id = r.role_id " +
                "WHERE u.email=:email";

        MapSqlParameterSource parameters = new MapSqlParameterSource(EMAIL, email);
        User user = null;

        try {
            user = jdbcTemplate.queryForObject(query, parameters, (resultSet, i) -> new User.Builder()
                    .setEmail(resultSet.getString(EMAIL))
                    .setPassword(resultSet.getString(PASSWORD))
                    .setRole(Role.valueOf(resultSet.getString(ROLE_NAME)))
                    .getInstance());
            LOGGER.debug("Retrieved user password, role, email=" + email);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("No email=" + email);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<Integer> getAllUserIds() {
        LOGGER.debug("Call - userDao.getAllUserIds() ;");
        String query = "SELECT user_id FROM Users ORDER BY user_id";
        return jdbcTemplate.query(query, new MapSqlParameterSource(),
                (resultSet, i) -> resultSet.getInt(USER_ID));
    }

    @Override
    public void saveImage(int userId, byte[] image, String imageType) {
        String query;
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(USER_ID, userId);

        if (PHOTO.equals(imageType)) {
            query = "UPDATE Users SET photo=:photo WHERE user_id=:user_id";
            parameters.addValue(PHOTO, image);
        } else if (IMAGE_PASSPORT_SCAN.equals(imageType)) {
            query = "UPDATE Users SET passport_scan=:passport_scan WHERE user_id=:user_id";
            parameters.addValue(PASSPORT_SCAN, image);
        } else {
            throw new RuntimeException("Image not saved: wrong image type description: " + imageType);
        }

        LOGGER.debug("Image saved");
        jdbcTemplate.update(query, parameters);
    }

    @Override
    public Optional<byte[]> getImage(int userId, String imageType) {
        byte[] image;
        String query;

        if (PHOTO.equals(imageType)) {
            query = "SELECT photo FROM Users WHERE user_id=:user_id";

        } else if (IMAGE_PASSPORT_SCAN.equals(imageType)) {
            query = "SELECT passport_scan FROM Users WHERE user_id=:user_id";

        } else {
            throw new RuntimeException("Wrong image type description: " + imageType);
        }

        image = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(USER_ID, userId), byte[].class);

        LOGGER.debug("Image of userId=" + userId + " retrieved");

        return Optional.ofNullable(image);
    }

    @Override
    public Optional<User> userForRegistrationCheck(String userEmail) {
        LOGGER.debug(String.format("Call - userForRegistrationCheck(%s) ;", userEmail));

        String query = "SELECT email, is_active FROM Users WHERE email =:email";
        User user = null;

        try {
            user = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(EMAIL, userEmail),
                    ((resultSet, i) -> new User.Builder()
                            .setEmail(resultSet.getString(EMAIL))
                            .setIsActive(resultSet.getInt(IS_ACTIVE) == 1)
                            .getInstance()));
            LOGGER.debug(String.format("isRegisteredEmail(%s) return (%s)", userEmail, user));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("isRegisteredEmail(%s) return empty user");
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("Get a list of all users");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u";
        return jdbcTemplate.query(query, (resultSet, i) -> new User.Builder().
                setUserId(resultSet.getInt(USER_ID)).
                setEmail(resultSet.getString(EMAIL)).
                setFirstName(resultSet.getString(FIRST_NAME)).
                setLastName(resultSet.getString(LAST_NAME)).
                setPhone(resultSet.getString(PHONE)).getInstance());
    }

    @Override
    public List<User> getUsersPerPages(DataNavigator dataNavigator) {
        LOGGER.debug("Get all user list");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                "LIMIT :from, :offset";

        MapSqlParameterSource parameters = daoUtil.getPagingSQLParameters(dataNavigator);

        List<User> users = jdbcTemplate.query(query, parameters, (resultSet, i) -> new User.Builder().
                setUserId(resultSet.getInt(USER_ID)).
                setEmail(resultSet.getString(EMAIL)).
                setFirstName(resultSet.getString(FIRST_NAME)).
                setLastName(resultSet.getString(LAST_NAME)).
                setPhone(resultSet.getString(PHONE)).getInstance());

        String allUsersEmail = users.stream().map(User::getEmail).collect(Collectors.joining("|"));
        LOGGER.debug("Quantity users -" + users.size());
        LOGGER.debug("All users list -" + allUsersEmail);

        return users;
    }

    @Override
    public int getQuantityUsers() {
        LOGGER.debug("Get all users list");
        String query = "SELECT count(*) FROM Users";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), int.class);
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
                "role_id = (SELECT r.role_id FROM Roles r WHERE r.name= :role)" +
                " WHERE user_id = :user_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(EMAIL, user.getEmail());
        parameters.addValue(FIRST_NAME, user.getFirstName());
        parameters.addValue(LAST_NAME, user.getLastName());
        parameters.addValue(PASSPORT_SCAN, user.getPassportScan());
        parameters.addValue(PHOTO, user.getPhoto());
        parameters.addValue(DISCOUNT, user.getDiscount());
        parameters.addValue(PHONE, user.getPhone());
        parameters.addValue(ROLE_NAME, user.getRole());
        parameters.addValue(USER_ID, user.getUserId());

        return jdbcTemplate.update(query, parameters);
    }

    public int activateUser(String email) {
        String query = "UPDATE Users " +
                "SET is_active = 1 " +
                "WHERE email = :email";

        return jdbcTemplate.update(query, new MapSqlParameterSource(EMAIL, email));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int addUserForCreateCertificate(User user) {
        String query = "INSERT INTO Users  (email, first_name, last_name, role_id) " +
                "VALUES (:email, :first_name, :last_name, (SELECT r.role_id FROM Roles r WHERE r.name='ROLE_USER'))";

        LOGGER.debug(String.format("Try add user -(%s) ;", user));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(EMAIL, user.getEmail());
        source.addValue(FIRST_NAME, user.getFirstName());
        source.addValue(LAST_NAME, user.getLastName());

        jdbcTemplate.update(query, source, keyHolder);

        LOGGER.debug(String.format("User added, user id -(%s) ;", keyHolder.getKey().toString()));
        return keyHolder.getKey().intValue();
    }


    @Override
    public List<User> searchUser(String userData) {
        LOGGER.debug(String.format("Call - userDao.searchUser(%s) ;", userData));

        String query = "SELECT user_id, email, first_name, last_name FROM Users WHERE email LIKE :userData " +
                "OR first_name LIKE :userData OR last_name LIKE :userData";

        LOGGER.debug(String.format("Search users by -(%s) ;", userData));
        return jdbcTemplate.query(query, new MapSqlParameterSource("userData", "%" + userData + "%"),
                (rs, i) -> new User.Builder().setUserId(rs.getInt(USER_ID))
                        .setEmail(rs.getString(EMAIL))
                        .setFirstName(rs.getString(FIRST_NAME))
                        .setLastName(rs.getString(LAST_NAME))
                        .getInstance());
    }

    @Override
    public void registrationUserInsert(User user) {
        LOGGER.info("Adding a new user into database");

        String query = "INSERT INTO Users (email, password, first_name, last_name, phone, role_id) " +
                "VALUES (:email, :password, :first_name, :last_name, :phone, " +
                "(SELECT role_id FROM Roles  WHERE name='ROLE_USER'))";

        jdbcTemplate.update(query, getRegistrationParameters(user));
    }

    @Override
    public void registrationUserUpdate(User user) {
        LOGGER.info("Update not active user .");

        String query = "UPDATE  Users SET password =:password, first_name =:first_name, last_name = :last_name, " +
                "phone =:phone, role_id = (SELECT r.role_id FROM Roles r WHERE r.name='ROLE_USER') WHERE email =:email";

        jdbcTemplate.update(query, getRegistrationParameters(user));
    }

    @Override
    public List<User> getTeachers() {
        LOGGER.debug("Trying to pull out all users with the role is a teacher.");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name,u.is_active, r.name FROM Users u " +
                "INNER JOIN Roles r  ON u.role_id = r.role_id WHERE r.name='ROLE_TEACHER' AND is_active=1";

        return jdbcTemplate.query(query, (resultSet, i) -> new User.Builder()
                .setUserId(resultSet.getInt(USER_ID))
                .setEmail(resultSet.getString(EMAIL))
                .setFirstName(resultSet.getString(FIRST_NAME))
                .setLastName(resultSet.getString(LAST_NAME))
                .setRole(Role.valueOf(resultSet.getString(ROLE_NAME)))
                .setIsActive(resultSet.getInt(IS_ACTIVE) == 1)
                .getInstance());
    }

    @Override
    public long setParamsToRestorePassword(String email, String uuid, LocalDateTime creationTime) {
        String query = "INSERT INTO Password_reset (email, uuid, creation_time) VALUES (:email, :uuid, :creationTime)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("email", email);
        source.addValue("uuid", uuid);
        source.addValue("creationTime", creationTime);

        jdbcTemplate.update(query, source, keyHolder);
        LOGGER.debug(String.format("Params to restore password for email=%s were saved", email));

        return keyHolder.getKey().longValue();
    }

    @Override
    public PasswordResetDto getEmailByUuid(long id, String uuid) {
        String query = "SELECT email, creation_time FROM Password_reset WHERE id =:id AND uuid =:uuid";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", id);
        source.addValue("uuid", uuid);
        PasswordResetDto dto;

        try {
            dto = jdbcTemplate.queryForObject(query, source, this::mapPasswordResetDto);
            LOGGER.debug(String.format("Password reset email %s was retrieved from DB", dto.getEmail()));
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Password reset email address was not found by ID and UUID");
        }
        return dto;
    }

    private PasswordResetDto mapPasswordResetDto(ResultSet resultSet, int i) throws SQLException {
        return PasswordResetDto.builder()
                .email(resultSet.getString("email"))
                .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                .build();
    }

    @Override
    public void savePassword(String email, String password) {
        String query = "UPDATE Users SET password =:password WHERE email=:email";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("email", email);
        source.addValue("password", password);

        int count = jdbcTemplate.update(query, source);
        if (count == 0) throw new UpdatedPasswordNotSaved("Updated password was not saved");
        LOGGER.debug(String.format("New password for email=%s was saved", email));
    }

    private MapSqlParameterSource getRegistrationParameters(User user) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(EMAIL, user.getEmail());
        namedParameters.addValue(PASSWORD, user.getPassword());
        namedParameters.addValue(FIRST_NAME, user.getFirstName());
        namedParameters.addValue(LAST_NAME, user.getLastName());
        namedParameters.addValue(PHONE, user.getPhone());
        return namedParameters;
    }

    @Override
    public List<User> getCourseUsers(int courseId) {
        LOGGER.debug(String.format("Try select all users by course id = (%s), from db.Accounting", courseId));

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name FROM Users u" +
                "  INNER JOIN Accounting a ON u.user_id = a.user_id WHERE course_id = :course_id";
        return jdbcTemplate.query(query, new MapSqlParameterSource(COURSE_ID, courseId),
                (resultSet, i) -> new User.Builder().setUserId(resultSet.getInt(USER_ID)).
                        setEmail(resultSet.getString(EMAIL)).
                        setFirstName(resultSet.getString(FIRST_NAME)).
                        setLastName(resultSet.getString(LAST_NAME)).getInstance());
    }

    private static final class UserRowMapping implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            LobHandler handler = new DefaultLobHandler();
            return new User.Builder()
                    .setUserId(resultSet.getInt(USER_ID))
                    .setEmail(resultSet.getString(EMAIL))
                    .setPassword(resultSet.getString(PASSWORD))
                    .setFirstName(resultSet.getString(FIRST_NAME))
                    .setLastName(resultSet.getString(LAST_NAME))
                    .setPassportScan(handler.getBlobAsBytes(resultSet, PASSPORT_SCAN))
                    .setPhoto(handler.getBlobAsBytes(resultSet, PHOTO))
                    .setDiscount(resultSet.getInt(DISCOUNT))
                    .setPhone(resultSet.getString(PHONE))
                    .setRole(Role.valueOf(resultSet.getString(ROLE_NAME)))
                    .getInstance();
        }
    }

    @Autowired
    public UserDaoImpl(@Qualifier(value = "DS") DataSource dataSource, DaoUtilInf daoUtil) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.daoUtil = daoUtil;
    }
}
