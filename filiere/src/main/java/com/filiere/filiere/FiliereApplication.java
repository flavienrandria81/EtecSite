package com.filiere.filiere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FiliereApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiliereApplication.class, args);
	}

}
