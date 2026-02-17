package com.example.Start_up_crm_client_service;

import com.example.Start_up_crm_client_service.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableConfigurationProperties(JwtConfig.class)
@SpringBootApplication
@EnableDiscoveryClient
public class StartUpCrmClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartUpCrmClientServiceApplication.class, args);
	}

}
