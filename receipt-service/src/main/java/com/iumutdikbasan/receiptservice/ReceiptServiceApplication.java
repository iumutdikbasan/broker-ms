package com.iumutdikbasan.receiptservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReceiptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptServiceApplication.class, args);
	}

}
