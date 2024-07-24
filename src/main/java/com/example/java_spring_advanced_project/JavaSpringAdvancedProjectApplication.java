package com.example.java_spring_advanced_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaSpringAdvancedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringAdvancedProjectApplication.class, args);
    }

}
