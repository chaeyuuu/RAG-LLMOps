package fisa.techseminar2;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {PgVectorStoreAutoConfiguration.class})
public class Techseminar2Application {

	public static void main(String[] args) {
		SpringApplication.run(Techseminar2Application.class, args);
	}

}
