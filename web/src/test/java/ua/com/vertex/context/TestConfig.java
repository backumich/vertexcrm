package ua.com.vertex.context;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import javax.validation.Validator;

@Configuration
@Import(RootConfig.class)
@ComponentScan("ua.com.vertex")
@Profile("test")
public class TestConfig {

    @Bean(name = "DS")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("embeddedDB.sql")
                .build();
    }

    @Bean
    public Validator validator() {

        return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();

    }
}
