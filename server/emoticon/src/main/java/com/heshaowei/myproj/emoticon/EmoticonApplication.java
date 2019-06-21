package com.heshaowei.myproj.emoticon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class EmoticonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmoticonApplication.class, args);
    }
}
