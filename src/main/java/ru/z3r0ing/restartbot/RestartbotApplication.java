package ru.z3r0ing.restartbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestartbotApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(RestartbotApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
