package com.heshaowei.myproj.im.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImCoreApplication.class, args);
    }
}
