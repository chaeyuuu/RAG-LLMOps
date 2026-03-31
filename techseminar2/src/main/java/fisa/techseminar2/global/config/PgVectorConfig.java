package fisa.techseminar2.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PgVectorConfig {

    @Value("${pgvector.datasource.url}")
    private String url;

    @Value("${pgvector.datasource.username}")
    private String username;

    @Value("${pgvector.datasource.password}")
    private String password;

    @Bean
    public JdbcTemplate pgVectorJdbcTemplate() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PgVectorStore vectorStore(
            JdbcTemplate pgVectorJdbcTemplate,
            EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(pgVectorJdbcTemplate, embeddingModel)
                .dimensions(768)
                .initializeSchema(true)
                .build();
    }
}