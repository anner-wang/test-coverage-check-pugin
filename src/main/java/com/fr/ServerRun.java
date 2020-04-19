package com.fr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServerRun {
    public static void main(String[] args) {
        SpringApplication.run(ServerRun.class, args);
    }
}
