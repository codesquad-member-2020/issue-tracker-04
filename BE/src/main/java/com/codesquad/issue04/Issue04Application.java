package com.codesquad.issue04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Issue04Application {
	public static void main(String[] args) {
		SpringApplication.run(Issue04Application.class, args);
	}
}
