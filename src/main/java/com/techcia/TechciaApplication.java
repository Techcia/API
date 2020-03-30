package com.techcia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class TechciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechciaApplication.class, args);
	}

	@RequestMapping("/home")
	public String hello() {
		return "Hello buddy!";
	}

}
