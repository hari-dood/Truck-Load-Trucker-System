package com.hariSolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TruckLoadTackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckLoadTackingSystemApplication.class, args);
	}

}
