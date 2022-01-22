package com.cactus.api.location.CactusLocationApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CactusLocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CactusLocationApiApplication.class, args);
	}

}
