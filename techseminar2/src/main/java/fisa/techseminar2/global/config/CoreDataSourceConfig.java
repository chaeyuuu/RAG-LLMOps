package fisa.techseminar2.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "fisa.techseminar2.repository.core",
        entityManagerFactoryRef = "coreEntityManagerFactory",
        transactionManagerRef = "coreTransactionManager"
)
public class CoreDataSourceConfig {

    @Value("${core.datasource.url}")
    private String coreUrl;

    @Value("${core.datasource.username}")
    private String coreUsername;

    @Value("${core.datasource.password}")
    private String corePassword;

    @Bean(name = "coreDataSource")
    public DataSource coreDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(coreUrl);
        ds.setUsername(coreUsername);
        ds.setPassword(corePassword);
        ds.setDriverClassName("org.postgresql.Driver");
        return ds;
    }

    @Bean(name = "coreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean coreEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(coreDataSource())
                .packages("fisa.techseminar2.domain.core")
                .persistenceUnit("core")
                .properties(Map.of(
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"
                ))
                .build();
    }

    @Bean(name = "coreTransactionManager")
    public PlatformTransactionManager coreTransactionManager(
            LocalContainerEntityManagerFactoryBean coreEntityManagerFactory) {
        return new JpaTransactionManager(
                Objects.requireNonNull(coreEntityManagerFactory.getObject())
        );
    }
}