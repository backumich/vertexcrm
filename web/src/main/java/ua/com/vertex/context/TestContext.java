package ua.com.vertex.context;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.com.vertex")
@Profile("test")
public class TestContext {

    @Bean
    public DataSource testDataSource() throws Exception {
        MainContext mainContext = new MainContext();
        return BasicDataSourceFactory.createDataSource(mainContext.getDbProperties());
    }

//    @Bean
//    public DataSource testDataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder
//                .setType(EmbeddedDatabaseType.HSQL)
//                .addScript("inMemoryDB/schema.sql")
//                .addScript("inMemoryDB/data.sql")
//                .build();
//    }
//
//    @PostConstruct
//    @Profile("test")
//    public void startDBManager() {
//        DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });
//    }
}
