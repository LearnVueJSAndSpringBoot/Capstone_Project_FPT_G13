package com.university.fpt.acf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.university.fpt.acf")
public class AcfApplication {
	public static void main(String[] args) {
		SpringApplication.run(AcfApplication.class, args);
	}

}
