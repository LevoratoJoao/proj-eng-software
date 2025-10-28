package com.software.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // Habilita configuração automática do Spring Boot
@EnableScheduling // Habilita o agendamento de tarefas (@Scheduled)
public class SoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareApplication.class, args);
	}

}
