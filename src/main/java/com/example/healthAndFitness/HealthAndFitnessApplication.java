package com.example.healthAndFitness;

import com.example.healthAndFitness.UploadFile.StorageProperties;
import com.example.healthAndFitness.UploadFile.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class HealthAndFitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthAndFitnessApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
