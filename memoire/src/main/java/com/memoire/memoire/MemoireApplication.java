package com.memoire.memoire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MemoireApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoireApplication.class, args);
	}

}
