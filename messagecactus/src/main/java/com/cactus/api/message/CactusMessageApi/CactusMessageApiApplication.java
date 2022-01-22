package com.cactus.api.message.CactusMessageApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CactusMessageApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CactusMessageApiApplication.class, args);
	}

}
