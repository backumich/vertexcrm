package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("ua.com.vertex")
public class TestContext {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("inMemoryDB/schema.sql")
                .build();
    }

    @Bean
    @Profile("testExternalDB")
    public DataSource dataSource2() throws Exception {
        return BasicDataSourceFactory.createDataSource(getDbProperties());
    }

    @Bean
    @Profile("testExternalDB")
    public Properties getDbProperties() throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource("testDBDoNotChange.properties");

        final PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(classPathResource);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }
}
