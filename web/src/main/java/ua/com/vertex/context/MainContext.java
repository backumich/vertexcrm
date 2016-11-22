package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.impl.Certificate;
import ua.com.vertex.dao.impl.CertificateDaoRealization;

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

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MainContext.class);
        CertificateDaoInf certificateDaoInf =  ctx.getBean(CertificateDaoRealization.class);
        Certificate test = certificateDaoInf.getCertificateById(2);
        System.out.println(test);
        certificateDaoInf.getAllCertificateByUserId(1).forEach(System.out::println);
    }

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
