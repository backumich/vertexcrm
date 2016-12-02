package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("ua.com.vertex")
//@EnableWebMvc
public class MainContext extends WebMvcConfigurerAdapter {

    private static final String DB_PROPERTIES = "db.properties";

    @Bean
    public DataSource dataSource() throws Exception {
        return BasicDataSourceFactory.createDataSource(getDbProperties());
    }

    public Properties getDbProperties() throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource(DB_PROPERTIES);

        final PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(classPathResource);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
//        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/pages/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }
}
