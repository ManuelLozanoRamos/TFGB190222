package upm.tfg.b190222.reviews_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ReviewsServiceApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(ReviewsServiceApplication.class, args);
    }
}