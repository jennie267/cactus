package com.cactus.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CactusProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CactusProductApiApplication.class, args);
	}

}
