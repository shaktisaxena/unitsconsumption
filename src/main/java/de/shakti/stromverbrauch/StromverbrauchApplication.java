package de.shakti.stromverbrauch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class StromverbrauchApplication {

	public static void main(String[] args) {
		SpringApplication.run(StromverbrauchApplication.class, args);
	}

}
