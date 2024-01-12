package com.example.ecommerceshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class EcommerceShopApplication {
    public static void main(String[] args) {

        SpringApplication.run(EcommerceShopApplication.class, args);
    }
}
