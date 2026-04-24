package com.example.Start_up_crm_client_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class StartUpCrmClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartUpCrmClientServiceApplication.class, args);
    }

}