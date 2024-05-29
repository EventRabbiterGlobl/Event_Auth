package event.com.Event_Auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EventAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventAuthServiceApplication.class, args);
	}

}
