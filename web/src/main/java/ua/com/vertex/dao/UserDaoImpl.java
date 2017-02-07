package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.utils.Storage;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static ua.com.vertex.beans.Role.ADMIN;
import static ua.com.vertex.beans.Role.USER;

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
    public User getUserDetailsByID(int userID) throws SQLException {
        String query = "SELECT u.user_id, u.email, u.password, u.first_name, u.last_name, u.passport_scan, " +
                "u.photo, u.discount, u.phone, u.role_id FROM Users u WHERE u.user_id=:userId";

        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource("userId", userID), new UserDetailsRowMapping());
    }

    private static final class UserDetailsRowMapping implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPassportScan(rs.getBytes("passport_scan"));
            user.setPhoto(rs.getBytes("photo"));
            user.setDiscount(rs.getInt("discount"));
            user.setPhone(rs.getString("phone"));
            user.setRole(rs.getInt("role_id") == 1 ? ADMIN : USER);
            return user;
        }
    }

//    private void getUserCertificatesFromDB(ResultSet rs, HashSet<Certificate> certificates) throws SQLException {
//        int certification_id = rs.getInt("certification_id");
//        if (!rs.wasNull() && certification_id > 0) {
//            Certificate certificate = new Certificate();
//            certificate.setCertificationId(certification_id);
//            if (rs.getDate("certification_date") != null) {
//                certificate.setCertificationDate(rs.getDate("certification_date").toLocalDate());
//            }
//            certificate.setCourseName(rs.getString("course_name"));
//            certificate.setLanguage(rs.getString("language"));
//            certificates.add(certificate);
//        }
//    }

//    private void getUserRolesFromDB(ResultSet rs, HashSet<Role> roles) throws SQLException {
//        int role_id = rs.getInt("role_id");
//        if (!rs.wasNull() && role_id > 0) {
//            Role role = new Role();
//            role.setRoleId(role_id);
//            role.setName(rs.getString("name"));
//            roles.add(role);
//        }
//    }

//    private User getUserFromDB(ResultSet rs, User user) throws SQLException {
//            if (user == null) {
//                user = new User();
//                user.setUserId(rs.getInt("user_id"));
//                user.setEmail(rs.getString("email"));
//                user.setFirstName(rs.getString("first_name"));
//                user.setLastName(rs.getString("last_name"));
//                user.setPassportScan(rs.getBytes("passport_scan"));
//                user.setPhoto(rs.getBytes("photo"));
//                user.setDiscount(rs.getInt("discount"));
//                user.setPhone(rs.getString("phone"));
//            }
//        return user;
//    }
//}

    @Override
    public List<User> getListUsers() throws SQLException {
        LOGGER.debug("Select list all users");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u";
        return jdbcTemplate.query(query, new UserDaoImpl.ViewAllUserRowMapping());
    }

    private static final class ViewAllUserRowMapping implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User.Builder().
                    setUserId(resultSet.getInt("user_id")).
                    setEmail(resultSet.getString("email")).
                    setFirstName(resultSet.getString("first_name")).
                    setLastName(resultSet.getString("last_name")).
                    setPhone(resultSet.getString("phone")).getInstance();
        }
    }

    @Override
    public HashMap<Role, Role> getListAllRoles() {
        LOGGER.debug("Select list all roles");

        String query = "SELECT r.role_id, r.name FROM Roles r";
        return jdbcTemplate.query(query, new GetListAllRolesRowMapping());
    }

    private static final class GetListAllRolesRowMapping implements ResultSetExtractor<HashMap<Role, Role>> {
        @Override
        public HashMap<Role, Role> extractData(ResultSet rs) throws SQLException {
            HashMap<Role, Role> allRoles = new HashMap<>();
            while (rs.next()) {
//                allRoles.put(rs.getInt("role_id"), rs.getString("name").equals("ADMIN") ? Role.ADMIN : Role.USER);
                allRoles.put(rs.getString("name").equals("ADMIN") ? Role.ADMIN : Role.USER,
                        rs.getString("name").equals("ADMIN") ? Role.ADMIN : Role.USER);
            }
            return allRoles;
        }
    }


//    @Override
//    public Role getRoleById(int roleID) throws SQLException {
//        LOGGER.debug("Select user role " + roleID);
//
//        String query = "SELECT r.role_id, r.name FROM Roles r WHERE r.role_id = :roleId";
//        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource("roleId", roleID), new RoleRowMapping());
//    }

//private static final class RoleRowMapping implements RowMapper<Role> {
//    public Role mapRow(ResultSet rs, int i) throws SQLException {
//        Role role = new Role();
//        role.setRoleId(rs.getInt("role_id"));
//        role.setName(rs.getString("name"));
//        return role;
//    }
//
//}

    @Override
    public int saveUserData(User user) {
        String query = "UPDATE Users " +
                "set email = :email , " +
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
        parameters.addValue("role_id", user.getRole() == Role.ADMIN ? 1 : 2);

        parameters.addValue("user_id", user.getUserId());

        return jdbcTemplate.update(query, parameters);
    }

    @Autowired
    public UserDaoImpl(DataSource dataSource, Storage storage) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.storage = storage;
    }
}
