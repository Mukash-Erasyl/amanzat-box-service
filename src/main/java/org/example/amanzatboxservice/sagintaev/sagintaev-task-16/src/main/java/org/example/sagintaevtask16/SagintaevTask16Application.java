package org.example.sagintaevtask16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class SagintaevTask16Application {

	public static void main(String[] args) {
		SpringApplication.run(SagintaevTask16Application.class, args);
	}

}
