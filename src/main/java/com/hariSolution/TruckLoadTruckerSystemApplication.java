package com.hariSolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TruckLoadTruckerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckLoadTruckerSystemApplication.class, args);
	}

}
