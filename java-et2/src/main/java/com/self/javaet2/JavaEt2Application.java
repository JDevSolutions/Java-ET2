package com.self.javaet2;


import com.self.javaet2.controller.WebController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JavaEt2Application {
	public static OrderBook book = new OrderBook();

	public static void main(String[] args) {
		SpringApplication.run(JavaEt2Application.class, args);

	}
}
