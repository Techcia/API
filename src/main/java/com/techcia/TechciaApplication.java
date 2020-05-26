package com.techcia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class TechciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechciaApplication.class, args);
	}

	@RequestMapping("/home")
	public ResponseEntity hello() throws ParseException {
		return ResponseEntity.ok("Hello");
	}

}
