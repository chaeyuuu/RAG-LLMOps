package fisa.techseminar2.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PgVectorConfig {

    @Value("${pgvector.datasource.url}")
    private String pgvectorUrl;

    @Value("${pgvector.datasource.username}")
    private String pgvectorUsername;

    @Value("${pgvector.datasource.password}")
    private String pgvectorPassword;

    @Bean(name = "pgVectorDataSource")
    public DataSource pgVectorDataSource() {
        return DataSourceBuilder.create()
                .url(pgvectorUrl)
                .username(pgvectorUsername)
                .password(pgvectorPassword)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
