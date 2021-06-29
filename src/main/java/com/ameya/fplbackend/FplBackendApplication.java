package com.ameya.fplbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ameya.fplbackend.security.AppProperties;

@SpringBootApplication
public class FplBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FplBackendApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean
	public AppProperties getAppProperties() {
		return new AppProperties();
	}

}
