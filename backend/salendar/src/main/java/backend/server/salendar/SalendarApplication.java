package backend.server.salendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalendarApplication.class, args);
	}

}
