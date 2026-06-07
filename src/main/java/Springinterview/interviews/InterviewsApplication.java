package Springinterview.interviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InterviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewsApplication.class, args);
	}
}