package com.alromaema.projects.moviecatalogsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
//@EnableAutoConfiguration

public class MoviecatalogApplication  {

	public static void main(String[] args) {
		SpringApplication.run(MoviecatalogApplication.class, args);
	}

}
