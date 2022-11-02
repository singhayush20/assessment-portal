package com.ayushsingh.assessmentportal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class AssessmentPortalApplication implements CommandLineRunner{
	private final String  CLASS_NAME=AssessmentPortalApplication.class.getName();
	public static void main(String[] args) {
		SpringApplication.run(AssessmentPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(CLASS_NAME+" starting project");
		
	}
	//Since the class is annotated with SpringBootApplication,
	//this is a configuration class and we can define the model mapper configuration
	//here
	@Bean //for autowiring
	public ModelMapper ModelMapper(){
		return new ModelMapper();
	}

}
