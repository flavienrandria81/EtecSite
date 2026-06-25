package com.etudiant.empoiDuTemps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmpoiDuTempsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpoiDuTempsApplication.class, args);
	}

}
