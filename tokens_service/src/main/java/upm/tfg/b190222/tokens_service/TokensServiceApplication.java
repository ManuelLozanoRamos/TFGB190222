package upm.tfg.b190222.tokens_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class TokensServiceApplication extends SpringBootServletInitializer {
    public static void main( String[] args ) {
        SpringApplication.run(TokensServiceApplication.class, args);
    }
}
