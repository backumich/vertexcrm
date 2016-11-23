package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.impl.Certificate;
import ua.com.vertex.dao.impl.CertificateDaoRealization;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("ua.com.vertex")
// todo: PropertySource now can be deleted
@PropertySource("classpath:db.properties")
public class MainContext {

    private static final String DB_PROPERTIES = "db.properties";

//    todo: these vars can also be deleted
    @Value("${driverClassName}")
    String driverClassName;

    @Value("${url}")
    String url;

    @Value("${username}")
    String username;

    @Value("${password}")
    String password;

    public static void main(String[] args) {
        //todo: do you really think this method should be commited?
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MainContext.class);
        CertificateDaoInf certificateDaoInf =  ctx.getBean(CertificateDaoRealization.class);
        Certificate test = certificateDaoInf.getCertificateById(2);
        System.out.println(test);
        certificateDaoInf.getAllCertificateByUserId(1).forEach(System.out::println);
    }

    @Bean
    public DataSource dataSource() throws Exception {

        //todo: clean up please
        /*-------DataSource for test and local work-------*/
        //DriverManagerDataSource result = new DriverManagerDataSource();

        /*
         * -------DataSource recommended Spring JDBC-------
         * @see <a href="http://www.baeldung.com/spring-jdbc-jdbctemplate> Spring JDBC </a>
         */


//        BasicDataSource result = new BasicDataSource();
//        result.setDriverClassName(driverClassName);
//        result.setUrl(url);
//        result.setUsername(username);
//        result.setPassword(password);
//        return result;

        return BasicDataSourceFactory.createDataSource(getDbProperties());
    }

    private Properties getDbProperties() throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource(DB_PROPERTIES);
        final PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(classPathResource);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }


}
