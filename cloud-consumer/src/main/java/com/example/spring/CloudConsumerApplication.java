package com.example.spring;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableHystrix
public class CloudConsumerApplication {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @GetMapping("/")
    public String home() {
        List<ServiceInstance> list = discoveryClient.getInstances("cloud-service");
        return list.stream()
                .map(instance -> instance.getUri().toString())
                .collect(Collectors.joining("||","", ""));
    }


    private String customFallBack() {
        return "Fall Back Error";
    }

    @HystrixCommand(fallbackMethod = "customFallBack")
    @GetMapping("/path2/")
    public String home2() {
        return restTemplate.getForObject("http://CLOUD-SERVICE/user", String.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(CloudConsumerApplication.class, args);
	}
}
