package com.cpa.accrt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LogindetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogindetailsApplication.class, args);
	}

}
