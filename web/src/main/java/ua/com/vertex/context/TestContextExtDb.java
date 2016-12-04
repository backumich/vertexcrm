package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("ua.com.vertex")
@Profile("testExternalDb")
public class TestContextExtDb {

    {
        System.out.println("=================================");
    }

    @Bean
    public DataSource dataSource() throws Exception {
        return BasicDataSourceFactory.createDataSource(getDbProperties());
    }

    @Bean
    public Properties getDbProperties() throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource("testDBDoNotChange.properties");

        final PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(classPathResource);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }
}
