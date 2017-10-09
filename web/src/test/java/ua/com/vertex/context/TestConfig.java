package ua.com.vertex.context;

import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.vertex.logic.interfaces.EmailLogic;
import ua.com.vertex.utils.MailService;
import ua.com.vertex.utils.ReCaptchaService;

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

    @Bean
    public ReCaptchaService reCaptchaService() {
        return Mockito.mock(ReCaptchaService.class);
    }

    @Bean
    public EmailLogic emailLogic() {
        return Mockito.mock(EmailLogic.class);
    }

    @Bean
    public MailService mailService() {
        return Mockito.mock(MailService.class);
    }
}
