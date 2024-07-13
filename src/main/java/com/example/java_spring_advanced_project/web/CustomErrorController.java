package com.example.java_spring_advanced_project.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class CustomErrorController implements ErrorController {

    private final ResourceLoader resourceLoader;

    public CustomErrorController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletResponse response) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/templates/error/error.html");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder htmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(htmlContent.toString());
        } catch (IOException e) {
            // Handle exception if the error page cannot be loaded
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading error page");
        }
    }


    public String getErrorPath() {
        return "/error";
    }
}
