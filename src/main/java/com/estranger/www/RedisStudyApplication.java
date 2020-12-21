package com.estranger.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RedisStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisStudyApplication.class, args);
	}

}
