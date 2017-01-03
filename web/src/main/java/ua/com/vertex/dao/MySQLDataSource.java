package ua.com.vertex.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

//import ua.com.vertex.models._User;

//todo: delete it please
public class MySQLDataSource {

    private DataSource dataSource = new MysqlDataSource();

    public MySQLDataSource(String url, String user, String password) {
        ((MysqlDataSource) dataSource).setURL(url);
        ((MysqlDataSource) dataSource).setUser(user);
        ((MysqlDataSource) dataSource).setPassword(password);
    }

//    public void addUser(_User user) {
//
//        String sql = "INSERT INTO Users (email, password, first_name, last_name, phone) VALUES (?,?,?,?,?)";
//        Connection conn = null;
//
//        try {
//            conn = dataSource.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            //ps.setInt(1, user.getCustId());
//
//            ps.setString(1, user.getEmail());
//            ps.setString(2, user.getPassword());
//            ps.setString(3, user.getFirstName());
//            ps.setString(4, user.getLastName());
//            ps.setString(5, user.getPhone());
//            ps.executeUpdate();
//            ps.close();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }


    @SuppressWarnings("unused")
    public DataSource getDataSource() {
        return dataSource;
    }
}
