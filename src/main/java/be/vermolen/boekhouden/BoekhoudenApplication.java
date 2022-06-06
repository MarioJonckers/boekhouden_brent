package be.vermolen.boekhouden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoekhoudenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoekhoudenApplication.class, args);
	}

}
