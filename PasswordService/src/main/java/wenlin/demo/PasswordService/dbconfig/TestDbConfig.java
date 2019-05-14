package wenlin.demo.PasswordService.dbconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class TestDbConfig {

    @Bean
    @Profile("test")
    @ConfigurationProperties(prefix = "test.datasource")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }
}
