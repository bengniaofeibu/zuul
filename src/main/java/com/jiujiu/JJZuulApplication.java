package com.jiujiu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JJZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(JJZuulApplication.class, args);
	}
}
