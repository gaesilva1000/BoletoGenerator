package com.gft.BoletoGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BoletoGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoletoGeneratorApplication.class, args);
	}

}
