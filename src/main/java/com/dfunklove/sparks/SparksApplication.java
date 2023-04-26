package com.dfunklove.sparks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SparksApplication {
	public static final int MAX_GOALS_PER_STUDENT = 3;

	public static void main(String[] args) {
		SpringApplication.run(SparksApplication.class, args);
	}

}
