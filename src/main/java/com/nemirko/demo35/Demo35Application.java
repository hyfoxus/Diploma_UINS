package com.nemirko.demo35;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Demo35Application {

	public static void main(String[] args) {

		SpringApplication.run(Demo35Application.class, args);
	}

}
