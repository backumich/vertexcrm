package ua.com.vertex.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.UserDaoInf;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@SuppressWarnings("SqlDialectInspection")
public class UserDaoRealization implements UserDaoInf {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    @Profile("dev")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    @Profile("test")
    public void setTestDataSource(DataSource testDataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(testDataSource);
    }

    @Override
    public User getUser(long id) {
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone FROM Users WHERE user_id=:id";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource("id", id), new UserRowMapping());
    }

    @Override
    public void deleteUser(long id) {
        String query = "DELETE FROM Users WHERE user_id=:id";
        jdbcTemplate.update(query, new MapSqlParameterSource("id", id));
    }

    @Override
    public List<Integer> getAllUserIds() {
        String query = "SELECT user_id FROM Users order by user_id";
        return jdbcTemplate.query(query, (resultSet, i) -> resultSet.getInt("user_id"));
    }

    private static final class UserRowMapping implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User.Builder()
                    .setUserId(resultSet.getInt("user_id"))
                    .setEmail(resultSet.getString("email"))
                    .setPassword(resultSet.getString("password"))
                    .setFirstName(resultSet.getString("first_name"))
                    .setLastName(resultSet.getString("last_name"))
                    .setPassportScan(resultSet.getBlob("passport_scan"))
                    .setPhoto(resultSet.getBlob("photo"))
                    .setDiscount(resultSet.getInt("discount"))
                    .setPhone(resultSet.getString("phone"))
                    .getInstance();
        }
    }

//    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(MainContext.class);
//        UserDaoRealization dao = context.getBean(UserDaoRealization.class);
//        User user = dao.getUser(50);
//        System.out.println(user);
//        ImageManager.saveImageToFileSystem(new File("d:/retrievedPhoto.jpg"), user.getPhoto());
//
//    }
}
