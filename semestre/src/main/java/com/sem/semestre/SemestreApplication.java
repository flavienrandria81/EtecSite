package com.sem.semestre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SemestreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SemestreApplication.class, args);
	}

}
