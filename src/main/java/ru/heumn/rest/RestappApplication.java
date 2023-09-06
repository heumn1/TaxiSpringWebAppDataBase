package ru.heumn.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.heumn.rest.domain.Passenger;
import ru.heumn.rest.repos.CarRepos;

@SpringBootApplication
public class RestappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestappApplication.class, args);
	}

}
