package com.heshaowei.myproj.cusform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerFormApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerFormApplication.class, args);
    }
}
