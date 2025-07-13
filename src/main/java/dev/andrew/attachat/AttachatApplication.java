package dev.andrew.attachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("dev.andrew.attachat.config.properties")
public class AttachatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttachatApplication.class, args);
	}

}
