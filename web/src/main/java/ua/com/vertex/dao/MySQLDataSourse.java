package ua.com.vertex.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public class MySQLDataSourse {

    private DataSource dataSource = new MysqlDataSource();

    public MySQLDataSourse(String url,String user, String password) {
        ((MysqlDataSource)dataSource).setURL(url);
        ((MysqlDataSource)dataSource).setUser(user);
        ((MysqlDataSource)dataSource).setPassword(password);
    }
    
    
@SuppressWarnings("unused")
    public DataSource getDataSource() {
        return dataSource;
    }
}
