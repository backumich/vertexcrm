package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.com.vertex")
@PropertySource("classpath:db.properties")
public class MainContext {

    @Value("${driver}")
    String driverClassName;

    @Value("${url}")
    String url;

    @Value("${user}")
    String username;

    @Value("${password}")
    String password;

    @Bean
    public DataSource dataSource() {

        /*-------DataSource for test and local work-------*/
        //DriverManagerDataSource result = new DriverManagerDataSource();

        /*
         * -------DataSource recommended Spring JDBC-------
         * @see <a href="http://www.baeldung.com/spring-jdbc-jdbctemplate> Spring JDBC </a>
         */
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(driverClassName);
        result.setUrl(url);
        result.setUsername(username);
        result.setPassword(password);
        return result;
    }


}
