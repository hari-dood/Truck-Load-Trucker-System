package com.hariSolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
public class TruckLoadManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckLoadManagementSystemApplication.class, args);
	}

}
