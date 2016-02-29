package mk.ukim.finki.emt.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
public class StoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(StoreApplication.class, args);
  }
}
