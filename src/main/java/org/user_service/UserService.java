package org.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserService {

    public static void main(String[] args) {
        SpringApplication.run(UserService.class, args);
        System.out.println("Hello World!");
    }
}