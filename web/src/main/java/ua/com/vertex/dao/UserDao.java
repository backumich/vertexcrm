package ua.com.vertex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainContext;
import ua.com.vertex.dao.impl.UserDaoRealization;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao implements UserDaoInf {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

    @Override
    public User getUser(long id) {
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone FROM Users WHERE user_id=:id";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource("id", id), new UserRowMapping());

//        User user = new User.Builder().getInstance();
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setLong(1, id);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    user = new User.Builder()
//                            .setUserId(resultSet.getInt("user_id"))
//                            .setEmail(resultSet.getString("email"))
//                            .setPassword(resultSet.getString("password"))
//                            .setFirstName(resultSet.getString("first_name"))
//                            .setLastName(resultSet.getString("last_name"))
//                            .setPassportScan(resultSet.getBlob("passport_scan"))
//                            .setPhoto(resultSet.getBlob("photo"))
//                            .setDiscount(resultSet.getInt("discount"))
//                            .setPhone(resultSet.getString("phone"))
//                            .getInstance();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone FROM Users";

//        try (Connection connection = dataSource.getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(query)) {
//
//            User user;
//            while (resultSet.next()) {
//                user = new User.Builder()
//                        .setUserId(resultSet.getInt("user_id"))
//                        .setEmail(resultSet.getString("email"))
//                        .setPassword(resultSet.getString("password"))
//                        .setFirstName(resultSet.getString("first_name"))
//                        .setLastName(resultSet.getString("last_name"))
//                        .setPassportScan(resultSet.getBlob("passport_scan"))
//                        .setPhoto(resultSet.getBlob("photo"))
//                        .setDiscount(resultSet.getInt("discount"))
//                        .setPhone(resultSet.getString("phone"))
//                        .getInstance();
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
        return users;
    }

    @Override
    public void deleteUser(long id) {
        String query1 = "DELETE FROM Certificate WHERE user_id=?";
        String query2 = "DELETE FROM Accounting WHERE user_id=?";
        String query3 = "DELETE FROM Users WHERE user_id=?";

//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement1 = connection.prepareStatement(query1);
//             PreparedStatement statement2 = connection.prepareStatement(query2);
//             PreparedStatement statement3 = connection.prepareStatement(query3)) {
//
//            statement1.setLong(1, id);
//            statement1.execute();
//
//            statement2.setLong(1, id);
//            statement2.execute();
//
//            statement3.setLong(1, id);
//            statement3.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void addUser(User user) {
        String query = "INSERT INTO Users (email, password, first_name, last_name, passport_scan, " +
                "photo, discount, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, user.getEmail());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getFirstName());
//            statement.setString(4, user.getLastName());
//            statement.setBlob(5, new SerialBlob(user.getPassportScan()));
//            statement.setBlob(6, new SerialBlob(user.getPhoto()));
//            statement.setInt(7, user.getDiscount());
//            statement.setString(8, user.getPhone());
//
//            statement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) throws IOException {

        ApplicationContext context = new AnnotationConfigApplicationContext(MainContext.class);
        UserDaoInf dao = context.getBean(UserDao.class);
        User user = dao.getUser(9);
        System.out.println(user);

//        UserDao userDao = new UserDao();

//        userDao.getAllUsers().forEach(System.out::println);

//        User user = userDao.getUser(9);
//        System.out.println(user);
//        ImageManager.saveImageToFileSystem(new File("d:/photo.jpg"), user.getPhoto());
//        ImageManager.saveImageToFileSystem(new File("d:/passport.jpg"), user.getPassportScan());

//        User user = new User.Builder()
//                .setEmail("email")
//                .setPassword("password")
//                .setFirstName("Expert")
//                .setLastName("Expert")
//                .setPassportScan(new File("d:/loza.jpg"))
//                .setPhoto(new File("d:/loza.jpg"))
//                .setDiscount(0)
//                .setPhone("+38 067 000 00 00")
//                .getInstance();
//
//        userDao.addUser(user);
//
//        userDao.deleteUser(10);
    }
}
