package com.java.zakhar.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.java.zakhar.App", "com.java.zakhar.Controllers"})
public class RestSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestSpringBootApplication.class, args);
    }
}
