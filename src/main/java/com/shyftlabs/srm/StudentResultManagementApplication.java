package com.shyftlabs.srm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Student Result Management"))
public class StudentResultManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentResultManagementApplication.class, args);
	}
	
}
