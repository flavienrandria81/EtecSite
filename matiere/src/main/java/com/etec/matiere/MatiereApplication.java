package com.etec.matiere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MatiereApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatiereApplication.class, args);
	}

}
