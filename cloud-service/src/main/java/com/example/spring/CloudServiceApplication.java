package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class CloudServiceApplication {

	@GetMapping("/user")
	public String home() {
		return "{name:Leon}";
	}

	@GetMapping("/user/{name}")
	public String home(@PathVariable String name) {
		return name;
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudServiceApplication.class, args);
	}
}
