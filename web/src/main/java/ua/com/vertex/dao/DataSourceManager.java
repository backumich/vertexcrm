package ua.com.vertex.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.IOException;
import java.util.Properties;

public class DataSourceManager {
    private static volatile MysqlDataSource instance = createDataSource();

    private DataSourceManager() {
    }

    private static MysqlDataSource createDataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();

        Properties properties = new Properties();
        try {
            properties.load(DataSourceManager.class.getClassLoader().getResourceAsStream("sva605db.properties"));
//            properties.load(DataSourceManager.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSource.setURL(properties.getProperty("url"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));

        return dataSource;
    }

    public static synchronized MysqlDataSource getInstance() {
        return (instance == null) ? createDataSource() : instance;
    }
}
