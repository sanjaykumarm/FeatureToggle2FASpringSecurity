package com.dange.tanmay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeatureToggleApplication {
    public static boolean forceEnable = false;
    public static void main(String[] args) {
        SpringApplication.run(FeatureToggleApplication.class, args);
    }
}